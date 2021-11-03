package uk.ac.reigate.dto.lookup;

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.lookup.StaffType
import uk.ac.reigate.dto.lookup.StaffTypeDto

import static org.junit.Assert.*



public class StaffTypeDtoTest {
    
    private StaffType staffType1
    
    private StaffType staffType2
    
    private List<StaffType> staffTypes
    
    @Before
    public void setup() {
        staffType1 = new StaffType(
                id: 1,
                code:'T',
                description:'Teacher',
                needInitials: true
                );
        staffType2 = new StaffType(
                id: 2,
                code:'S',
                description:'Support Staff',
                needInitials: true
                );
        staffTypes = Arrays.asList(staffType1, staffType2);
    }
    
    @Test
    public void testMapFromStaffTypeEntity(){
        StaffTypeDto staffTypeTest = StaffTypeDto.mapFromEntity( staffType1 )
        assertEquals( staffTypeTest.id, staffType1.id );
        assertEquals( staffTypeTest.code, staffType1.code);
        assertEquals( staffTypeTest.description, staffType1.description);
    }
    
    @Test
    public void testMapFromStaffTypesEntities(){
        List<StaffTypeDto> staffTypesDtoTest = StaffTypeDto.mapFromList( staffTypes )
        assertEquals( staffTypesDtoTest[0].id, staffType1.id );
        assertEquals( staffTypesDtoTest[0].code, staffType1.code);
        assertEquals( staffTypesDtoTest[0].description, staffType1.description);
        assertEquals( staffTypesDtoTest[1].id, staffType2.id );
        assertEquals( staffTypesDtoTest[1].code, staffType2.code);
        assertEquals( staffTypesDtoTest[1].description, staffType2.description);
    }
    
    @Test
    public void testEquals_Same() {
        StaffTypeDto staffTypeDto1 = new StaffTypeDto(staffType1)
        StaffTypeDto staffTypeDto2 = new StaffTypeDto(staffType1)
        assertEquals(staffTypeDto1, staffTypeDto2)
    }
    
    @Test
    public void testEquals_Different() {
        StaffTypeDto staffTypeDto1 = new StaffTypeDto(staffType1)
        StaffTypeDto staffTypeDto2 = new StaffTypeDto(staffType2)
        assertNotEquals(staffTypeDto1, staffTypeDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        StaffTypeDto staffTypeDto1 = new StaffTypeDto(staffType1)
        StaffTypeDto staffTypeDto2 = new StaffTypeDto(staffType1)
        assertEquals(staffTypeDto1.hashCode(), staffTypeDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        StaffTypeDto staffTypeDto1 = new StaffTypeDto(staffType1)
        StaffTypeDto staffTypeDto2 = new StaffTypeDto(staffType2)
        assertNotEquals(staffTypeDto1.hashCode(), staffTypeDto2.hashCode())
    }
    
    @Test
    public void testConstructor_StaffType() {
        StaffTypeDto staffTypeDto = new StaffTypeDto(staffType1)
        assertEquals( staffTypeDto.code, staffType1.code )
        assertEquals( staffTypeDto.description, staffType1.description )
    }
}
