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

package org.hisp.dhis.android.core.trackedentity;

import android.database.Cursor;
import android.support.test.runner.AndroidJUnit4;

import org.hisp.dhis.android.core.common.BaseIdentifiableObject;
import org.hisp.dhis.android.core.data.database.AbsStoreTestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;

import static com.google.common.truth.Truth.assertThat;
import static org.hisp.dhis.android.core.data.database.CursorAssert.assertThatCursor;

@RunWith(AndroidJUnit4.class)
public class TrackedEntityStoreTests extends AbsStoreTestCase {
    public static final String[] PROJECTION = {
            TrackedEntityModel.Columns.UID,
            TrackedEntityModel.Columns.CODE,
            TrackedEntityModel.Columns.NAME,
            TrackedEntityModel.Columns.DISPLAY_NAME,
            TrackedEntityModel.Columns.CREATED,
            TrackedEntityModel.Columns.LAST_UPDATED,
            TrackedEntityModel.Columns.SHORT_NAME,
            TrackedEntityModel.Columns.DISPLAY_SHORT_NAME,
            TrackedEntityModel.Columns.DESCRIPTION,
            TrackedEntityModel.Columns.DISPLAY_DESCRIPTION,
    };
    public static final String UID = "uid";
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String DISPLAY_NAME = "display_name";
    public static final String SHORT_NAME = "short_name";
    public static final String DISPLAY_SHORT_NAME = "display_short_name";
    public static final String DESCRIPTION = "description";
    public static final String DISPLAY_DESCRIPTION = "display_description";

    private final Date date;
    private final String dateString;

    public TrackedEntityStoreTests() {
        this.date = new Date();
        this.dateString = BaseIdentifiableObject.DATE_FORMAT.format(date);
    }

    private TrackedEntityStore trackedEntityStore;

    @Before
    @Override
    public void setUp() throws IOException {
        super.setUp();
        trackedEntityStore = new TrackedEntityStoreImpl(databaseAdapter());
    }

    @Test
    public void insert_shouldPersistRowInDatabase() {
        long rowId = trackedEntityStore.insert(
                UID,
                CODE,
                NAME,
                DISPLAY_NAME,
                date,
                date,
                SHORT_NAME,
                DISPLAY_SHORT_NAME,
                DESCRIPTION,
                DISPLAY_DESCRIPTION
        );
        Cursor cursor = database().query(TrackedEntityModel.TABLE, PROJECTION, null, null, null, null, null);

        assertThat(rowId).isEqualTo(1L);
        assertThatCursor(cursor).hasRow(
                UID,
                CODE,
                NAME,
                DISPLAY_NAME,
                dateString,
                dateString,
                SHORT_NAME,
                DISPLAY_SHORT_NAME,
                DESCRIPTION,
                DISPLAY_DESCRIPTION
        ).isExhausted();
    }

    // ToDo: consider introducing conflict resolution strategy

    @Test
    public void close_shouldNotCloseDatabase() {
        trackedEntityStore.close();
        assertThat(database().isOpen()).isTrue();
    }
}
