/*
 * Copyright (c) 2017, University of Oslo
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

package org.hisp.dhis.android.core.program;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

import org.hisp.dhis.android.core.common.BaseIdentifiableObject;
import org.hisp.dhis.android.core.common.FormType;

import java.util.Date;
import java.util.List;

import static org.hisp.dhis.android.core.common.Utils.safeUnmodifiableList;

@AutoValue
public abstract class ProgramStage extends BaseIdentifiableObject {
    private static final String EXECUTION_DATE_LABEL = "executionDateLabel";
    private static final String ALLOW_GENERATE_NEXT_VISIT = "allowGenerateNextVisit";
    private static final String VALID_COMPLETE_ONLY = "validCompleteOnly";
    private static final String REPORT_DATE_TO_USE = "reportDateToUse";
    private static final String OPEN_AFTER_ENROLLMENT = "openAfterEnrollment";
    private static final String PROGRAM_STAGE_DATA_ELEMENTS = "programStageDataElements";
    private static final String REPEATABLE = "repeatable";
    private static final String CAPTURE_COORDINATES = "captureCoordinates";
    private static final String FORM_TYPE = "formType";
    private static final String DISPLAY_GENERATE_EVENT_BOX = "displayGenerateEventBox";
    private static final String GENERATED_BY_ENROLMENT_DATE = "generatedByEnrollmentDate";
    private static final String AUTO_GENERATE_EVENT = "autoGenerateEvent";
    private static final String SORT_ORDER = "sortOrder";
    private static final String HIDE_DUE_DATE = "hideDueDate";
    private static final String BLOCK_ENTRY_FORM = "blockEntryForm";
    private static final String MIN_DAYS_FROM_START = "minDaysFromStart";
    private static final String STANDARD_INTERVAL = "standardInterval";
    private static final String PROGRAM_STAGE_SECTIONS = "programStageSections";

    @Nullable
    @JsonProperty(EXECUTION_DATE_LABEL)
    public abstract String executionDateLabel();

    @Nullable
    @JsonProperty(ALLOW_GENERATE_NEXT_VISIT)
    public abstract Boolean allowGenerateNextVisit();

    @Nullable
    @JsonProperty(VALID_COMPLETE_ONLY)
    public abstract Boolean validCompleteOnly();

    @Nullable
    @JsonProperty(REPORT_DATE_TO_USE)
    public abstract String reportDateToUse();

    @Nullable
    @JsonProperty(OPEN_AFTER_ENROLLMENT)
    public abstract Boolean openAfterEnrollment();

    @Nullable
    @JsonProperty(REPEATABLE)
    public abstract Boolean repeatable();

    @Nullable
    @JsonProperty(CAPTURE_COORDINATES)
    public abstract Boolean captureCoordinates();

    @Nullable
    @JsonProperty(FORM_TYPE)
    public abstract FormType formType();

    @Nullable
    @JsonProperty(DISPLAY_GENERATE_EVENT_BOX)
    public abstract Boolean displayGenerateEventBox();

    @Nullable
    @JsonProperty(GENERATED_BY_ENROLMENT_DATE)
    public abstract Boolean generatedByEnrollmentDate();

    @Nullable
    @JsonProperty(AUTO_GENERATE_EVENT)
    public abstract Boolean autoGenerateEvent();

    @Nullable
    @JsonProperty(SORT_ORDER)
    public abstract Integer sortOrder();

    @Nullable
    @JsonProperty(HIDE_DUE_DATE)
    public abstract Boolean hideDueDate();

    @Nullable
    @JsonProperty(BLOCK_ENTRY_FORM)
    public abstract Boolean blockEntryForm();

    @Nullable
    @JsonProperty(MIN_DAYS_FROM_START)
    public abstract Integer minDaysFromStart();

    @Nullable
    @JsonProperty(STANDARD_INTERVAL)
    public abstract Integer standardInterval();

    @Nullable
    @JsonProperty(PROGRAM_STAGE_SECTIONS)
    public abstract List<ProgramStageSection> programStageSections();

    @Nullable
    @JsonProperty(PROGRAM_STAGE_DATA_ELEMENTS)
    public abstract List<ProgramStageDataElement> programStageDataElements();

    @JsonCreator
    public static ProgramStage create(
            @JsonProperty(UID) String uid,
            @JsonProperty(CODE) String code,
            @JsonProperty(NAME) String name,
            @JsonProperty(DISPLAY_NAME) String displayName,
            @JsonProperty(CREATED) Date created,
            @JsonProperty(LAST_UPDATED) Date lastUpdated,
            @JsonProperty(EXECUTION_DATE_LABEL) String executionDateLabel,
            @JsonProperty(ALLOW_GENERATE_NEXT_VISIT) Boolean allowGenerateNextVisit,
            @JsonProperty(VALID_COMPLETE_ONLY) Boolean validCompleteOnly,
            @JsonProperty(REPORT_DATE_TO_USE) String reportDateToUse,
            @JsonProperty(OPEN_AFTER_ENROLLMENT) Boolean openAfterEnrollment,
            @JsonProperty(REPEATABLE) Boolean repeatable,
            @JsonProperty(CAPTURE_COORDINATES) Boolean captureCoordinates,
            @JsonProperty(FORM_TYPE) FormType formType,
            @JsonProperty(DISPLAY_GENERATE_EVENT_BOX) Boolean displayGenerateEventBox,
            @JsonProperty(GENERATED_BY_ENROLMENT_DATE) Boolean generatedByEnrollmentDate,
            @JsonProperty(AUTO_GENERATE_EVENT) Boolean autoGenerateEvent,
            @JsonProperty(SORT_ORDER) Integer sortOrder,
            @JsonProperty(HIDE_DUE_DATE) Boolean hideDueDate,
            @JsonProperty(BLOCK_ENTRY_FORM) Boolean blockEntryForm,
            @JsonProperty(MIN_DAYS_FROM_START) Integer minDaysFromStart,
            @JsonProperty(STANDARD_INTERVAL) Integer standardInterval,
            @JsonProperty(PROGRAM_STAGE_SECTIONS) List<ProgramStageSection> programStageSections,
            @JsonProperty(PROGRAM_STAGE_DATA_ELEMENTS) List<ProgramStageDataElement> programStageDataElements
    ) {

        return new AutoValue_ProgramStage(
                uid,
                code,
                name,
                displayName,
                created,
                lastUpdated,
                executionDateLabel,
                allowGenerateNextVisit,
                validCompleteOnly,
                reportDateToUse,
                openAfterEnrollment,
                repeatable,
                captureCoordinates,
                formType,
                displayGenerateEventBox,
                generatedByEnrollmentDate,
                autoGenerateEvent,
                sortOrder,
                hideDueDate,
                blockEntryForm,
                minDaysFromStart,
                standardInterval,
                safeUnmodifiableList(programStageSections),
                safeUnmodifiableList(programStageDataElements)
        );
    }
}