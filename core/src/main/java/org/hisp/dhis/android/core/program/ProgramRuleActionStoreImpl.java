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

import android.database.sqlite.SQLiteStatement;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.hisp.dhis.android.core.data.database.DatabaseAdapter;

import java.util.Date;

import static org.hisp.dhis.android.core.common.StoreUtils.sqLiteBind;

public class ProgramRuleActionStoreImpl implements ProgramRuleActionStore {
    private static final String INSERT_STATEMENT = "INSERT INTO " + ProgramRuleActionModel.TABLE + " (" +
            ProgramRuleActionModel.Columns.UID + ", " +
            ProgramRuleActionModel.Columns.CODE + ", " +
            ProgramRuleActionModel.Columns.NAME + ", " +
            ProgramRuleActionModel.Columns.DISPLAY_NAME + ", " +
            ProgramRuleActionModel.Columns.CREATED + ", " +
            ProgramRuleActionModel.Columns.LAST_UPDATED + ", " +
            ProgramRuleActionModel.Columns.DATA + ", " +
            ProgramRuleActionModel.Columns.CONTENT + ", " +
            ProgramRuleActionModel.Columns.LOCATION + ", " +
            ProgramRuleActionModel.Columns.TRACKED_ENTITY_ATTRIBUTE + ", " +
            ProgramRuleActionModel.Columns.PROGRAM_INDICATOR + ", " +
            ProgramRuleActionModel.Columns.PROGRAM_STAGE_SECTION + ", " +
            ProgramRuleActionModel.Columns.PROGRAM_RULE_ACTION_TYPE + ", " +
            ProgramRuleActionModel.Columns.PROGRAM_STAGE + ", " +
            ProgramRuleActionModel.Columns.DATA_ELEMENT + ", " +
            ProgramRuleActionModel.Columns.PROGRAM_RULE +
            ") " + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private final SQLiteStatement insertRowStatement;
    private final DatabaseAdapter databaseAdapter;

    public ProgramRuleActionStoreImpl(DatabaseAdapter databaseAdapter) {
        this.databaseAdapter = databaseAdapter;
        this.insertRowStatement = databaseAdapter.compileStatement(INSERT_STATEMENT);
    }


    @Override
    public long insert(@NonNull String uid, @Nullable String code, @NonNull String name,
                       @Nullable String displayName, @NonNull Date created,
                       @NonNull Date lastUpdated, @Nullable String data, @Nullable String content,
                       @Nullable String location, @Nullable String trackedEntityAttribute,
                       @Nullable String programIndicator, @Nullable String programStageSection,
                       @NonNull ProgramRuleActionType programRuleActionType,
                       @Nullable String programStage, @Nullable String dataElement,
                       @Nullable String programRule) {

        insertRowStatement.clearBindings();

        sqLiteBind(insertRowStatement, 1, uid);
        sqLiteBind(insertRowStatement, 2, code);
        sqLiteBind(insertRowStatement, 3, name);
        sqLiteBind(insertRowStatement, 4, displayName);
        sqLiteBind(insertRowStatement, 5, created);
        sqLiteBind(insertRowStatement, 6, lastUpdated);
        sqLiteBind(insertRowStatement, 7, data);
        sqLiteBind(insertRowStatement, 8, content);
        sqLiteBind(insertRowStatement, 9, location);
        sqLiteBind(insertRowStatement, 10, trackedEntityAttribute);
        sqLiteBind(insertRowStatement, 11, programIndicator);
        sqLiteBind(insertRowStatement, 12, programStageSection);
        sqLiteBind(insertRowStatement, 13, programRuleActionType);
        sqLiteBind(insertRowStatement, 14, programStage);
        sqLiteBind(insertRowStatement, 15, dataElement);
        sqLiteBind(insertRowStatement, 16, programRule);

        return databaseAdapter.executeInsert(ProgramRuleActionModel.TABLE, insertRowStatement);
    }

    @Override
    public void close() {
        insertRowStatement.close();
    }
}
