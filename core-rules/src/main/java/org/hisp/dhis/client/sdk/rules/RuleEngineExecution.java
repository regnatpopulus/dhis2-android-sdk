/*
 * Copyright (c) 2016, University of Oslo
 *
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.hisp.dhis.client.sdk.rules;

import org.apache.commons.jexl2.JexlException;
import org.hisp.dhis.client.sdk.models.program.ProgramRule;
import org.hisp.dhis.client.sdk.models.program.ProgramRuleAction;
import org.hisp.dhis.commons.util.ExpressionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RuleEngineExecution {
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("[A#CV]\\{(\\w+.?\\w*)\\}");

    public static List<RuleEffect> execute(
            List<ProgramRule> rules, RuleEngineVariableValueMap variableValueMap) {

        ArrayList<RuleEffect> effects = new ArrayList<>();
        for (ProgramRule rule : rules) {
            if (conditionIsTrue(rule.getCondition(), variableValueMap)) {
                for (ProgramRuleAction action : rule.getProgramRuleActions()) {
                    effects.add(createEffect(action));
                }
            }
        }

        return effects;
    }

    private static String replaceVariables(
            String expression, RuleEngineVariableValueMap variableValueMap) {

        System.out.println("ProgramRuleVariableMap: " + variableValueMap);

        List<String> variablesFound = new ArrayList<>();
        Matcher matcher = VARIABLE_PATTERN.matcher(expression);

        while (matcher.find()) {
            String variable = expression.substring(matcher.start(), matcher.end());
            variablesFound.add(variable);
        }

        System.out.println("Expression before: " + expression);
        for (String variable : variablesFound) {
            String variableName = variable.replace("#{", "").replace("}", "");

            System.out.println("Expression variable: " + variableName);

            ProgramRuleVariableValue variableValue = variableValueMap
                    .getProgramRuleVariableValue(variableName);
            String variableValueString = variableValue != null ? variableValue.toString() : "";

            if (variableValueString.length() != 0) {
                expression = expression.replace(variable, variableValueString);
            } else {
                // get value type of data element
                // provide sensible fallback value for it
                // basically, we can have three types: boolean, integer, string

                // TODO: replace with logging
                System.out.println("No value found for variable: " + variable);
            }

//            if (variableValue != null) {
//                expression = expression.replace(variable, variableValue.toString());
//            } else {
//                // TODO Log the problem - the expression contains a variable that is not defined
//                throw new IllegalArgumentException("Variable " + variableName +
//                        " found in expression " + expression + ", but is not defined as a variable");
//            }
        }

        System.out.println("Expression after: " + expression);

        return expression;
    }

    /**
     * Evaluates a passed expression from a {@link ProgramRule} to true or false.
     *
     * @param condition
     * @return
     */
    private static boolean conditionIsTrue(
            String condition, RuleEngineVariableValueMap variableValueMap) {

        condition = replaceVariables(condition, variableValueMap);

        try {
            return ExpressionUtils.isTrue(condition, null);
        } catch (JexlException jxlException) {
            jxlException.printStackTrace();
        }

        return false;
    }

    /**
     * Mapping method for creating a {@link RuleEffect} from a {@link ProgramRuleAction} object.
     *
     * @param action
     * @return
     */
    private static RuleEffect createEffect(ProgramRuleAction action) {
        RuleEffect effect = new RuleEffect();

        effect.setProgramRule(action.getProgramRule());
        effect.setProgramRuleActionType(action.getProgramRuleActionType());
        effect.setContent(action.getContent());
        effect.setData(action.getData());
        effect.setDataElement(action.getDataElement());
        effect.setProgramIndicator(action.getProgramIndicator());
        effect.setLocation(action.getLocation());
        effect.setProgramStage(action.getProgramStage());
        effect.setProgramStageSection(action.getProgramStageSection());
        effect.setTrackedEntityAttribute(action.getTrackedEntityAttribute());

        return effect;
    }
}
