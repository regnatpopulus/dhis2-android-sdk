package org.hisp.dhis.android.models.dataelement;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class DataElementTests {

    private static DataElement.Builder VALID_BUILDER;

    @Before
    public void setUp() {
        VALID_BUILDER = createValidBuilder();
    }

    private DataElement.Builder createValidBuilder() {
        return DataElement.builder()
                .uid("a1b2c3d4e5f")
                .created(new java.util.Date())
                .lastUpdated(new java.util.Date());
    }

    //**************************************************************************************
    //
    // BASE IDENTIFIABLE OBJECT TESTS
    //
    //**************************************************************************************

    @Test(expected = IllegalStateException.class)
    public void build_shouldThrowOnNullUidField() {
        VALID_BUILDER.uid(null).build();
    }

    //**************************************************************************************
    //
    // EQUALS VERIFIER
    //
    //**************************************************************************************

    @Test
    public void equals_shouldConformToContract() {
        EqualsVerifier.forClass(VALID_BUILDER.build().getClass())
                .suppress(Warning.NULL_FIELDS)
                .verify();
    }
}
