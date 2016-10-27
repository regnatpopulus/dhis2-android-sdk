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

package org.hisp.dhis.client.sdk.core.organisationunit;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import org.hisp.dhis.client.sdk.core.commons.database.Mapper;
import org.hisp.dhis.client.sdk.core.organisationunit.OrganisationUnitTable.OrganisationUnitColumns;
import org.hisp.dhis.client.sdk.models.common.BaseIdentifiableObject;
import org.hisp.dhis.client.sdk.models.organisationunit.OrganisationUnit;

import java.text.ParseException;

import static org.hisp.dhis.client.sdk.core.commons.database.DbUtils.getInt;
import static org.hisp.dhis.client.sdk.core.commons.database.DbUtils.getString;

public class OrganisationUnitMapper implements Mapper<OrganisationUnit> {

    @Override
    public Uri getContentUri() {
        return OrganisationUnitTable.CONTENT_URI;
    }

    @Override
    public Uri getContentItemUri(long id) {
        return ContentUris.withAppendedId(OrganisationUnitTable.CONTENT_URI, id);
    }

    @Override
    public String[] getProjection() {
        return OrganisationUnitTable.PROJECTION;
    }

    @Override
    public ContentValues toContentValues(OrganisationUnit orgUnit) {
        OrganisationUnit.validate(orgUnit);

        ContentValues contentValues = new ContentValues();
        contentValues.put(OrganisationUnitColumns.COLUMN_ID, orgUnit.getId());
        contentValues.put(OrganisationUnitColumns.COLUMN_UID, orgUnit.getUid());
        contentValues.put(OrganisationUnitColumns.COLUMN_CODE, orgUnit.getCode());
        contentValues.put(OrganisationUnitColumns.COLUMN_CREATED, orgUnit.getCreated().toString());
        contentValues.put(OrganisationUnitColumns.COLUMN_LAST_UPDATED, orgUnit.getLastUpdated().toString());
        contentValues.put(OrganisationUnitColumns.COLUMN_NAME, orgUnit.getName());
        contentValues.put(OrganisationUnitColumns.COLUMN_DISPLAY_NAME, orgUnit.getDisplayName());
        contentValues.put(OrganisationUnitColumns.COLUMN_SHORT_NAME, orgUnit.getShortName());
        contentValues.put(OrganisationUnitColumns.COLUMN_DISPLAY_SHORT_NAME, orgUnit.getDisplayShortName());
        contentValues.put(OrganisationUnitColumns.COLUMN_DESCRIPTION, orgUnit.getDescription());
        contentValues.put(OrganisationUnitColumns.COLUMN_DISPLAY_DESCRIPTION, orgUnit.getDisplayDescription());

        contentValues.put(OrganisationUnitColumns.COLUMN_PARENT,
                orgUnit.getParent() != null ? orgUnit.getParent().getUid() : null);
        contentValues.put(OrganisationUnitColumns.COLUMN_OPENING_DATE,
                orgUnit.getOpeningDate() != null ? orgUnit.getOpeningDate().toString() : null);
        contentValues.put(OrganisationUnitColumns.COLUMN_CLOSED_DATE,
                orgUnit.getClosedDate() != null ? orgUnit.getClosedDate().toString() : null);
        contentValues.put(OrganisationUnitColumns.COLUMN_LEVEL, orgUnit.getLevel());
        contentValues.put(OrganisationUnitColumns.COLUMN_PATH, orgUnit.getPath());

        return contentValues;
    }

    @Override
    public OrganisationUnit toModel(Cursor cursor) {
        OrganisationUnit organisationUnit = new OrganisationUnit();
        organisationUnit.setId(getInt(cursor, OrganisationUnitColumns.COLUMN_ID));
        organisationUnit.setUid(getString(cursor, OrganisationUnitColumns.COLUMN_UID));
        organisationUnit.setCode(getString(cursor, OrganisationUnitColumns.COLUMN_CODE));
        organisationUnit.setName(getString(cursor, OrganisationUnitColumns.COLUMN_NAME));
        organisationUnit.setDisplayName(getString(cursor, OrganisationUnitColumns.COLUMN_DISPLAY_NAME));

        organisationUnit.setShortName(getString(cursor, OrganisationUnitColumns.COLUMN_SHORT_NAME));
        organisationUnit.setDisplayShortName(getString(cursor, OrganisationUnitColumns.COLUMN_DISPLAY_SHORT_NAME));
        organisationUnit.setDescription(getString(cursor, OrganisationUnitColumns.COLUMN_DISPLAY_DESCRIPTION));
        organisationUnit.setDisplayDescription(getString(cursor, OrganisationUnitColumns.COLUMN_DISPLAY_DESCRIPTION));

        OrganisationUnit parent = new OrganisationUnit();
        parent.setUid(getString(cursor, OrganisationUnitColumns.COLUMN_PARENT));

        organisationUnit.setParent(parent);
        organisationUnit.setLevel(getInt(cursor, OrganisationUnitColumns.COLUMN_LEVEL));
        organisationUnit.setPath(getString(cursor, OrganisationUnitColumns.COLUMN_PATH));

        try {
            organisationUnit.setCreated(BaseIdentifiableObject.SIMPLE_DATE_FORMAT
                    .parse(getString(cursor, OrganisationUnitColumns.COLUMN_CREATED)));
            organisationUnit.setLastUpdated(BaseIdentifiableObject.SIMPLE_DATE_FORMAT
                    .parse(getString(cursor, OrganisationUnitColumns.COLUMN_LAST_UPDATED)));

            if (getString(cursor, OrganisationUnitColumns.COLUMN_OPENING_DATE) != null) {
                organisationUnit.setOpeningDate(BaseIdentifiableObject.SIMPLE_DATE_FORMAT
                        .parse(getString(cursor, OrganisationUnitColumns.COLUMN_OPENING_DATE)));
            }

            if (getString(cursor, OrganisationUnitColumns.COLUMN_CLOSED_DATE) != null) {
                organisationUnit.setClosedDate(BaseIdentifiableObject.SIMPLE_DATE_FORMAT
                        .parse(getString(cursor, OrganisationUnitColumns.COLUMN_CLOSED_DATE)));
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }

        return organisationUnit;
    }
}