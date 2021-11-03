package uk.ac.reigate.dto.ilp;

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.ilp.ILPInterviewType
import uk.ac.reigate.dto.ilp.ILPInterviewTypeDto

public class ILPInterviewTypeDtoTest {
    
    private ILPInterviewType iLPInterviewType1
    
    private ILPInterviewType iLPInterviewType2
    
    private List<ILPInterviewType> iLPInterviewTypes
    
    @Before
    public void setup() {
        this.iLPInterviewType1 = new ILPInterviewType(
                id: 1,
                type: 'A'
                );
        this.iLPInterviewType2 = new ILPInterviewType(
                id: 2,
                type: 'B'
                );
        this.iLPInterviewTypes = Arrays.asList(this.iLPInterviewType1, this.iLPInterviewType2);
    }
    
    @Test
    public void testMapFromILPInterviewTypeEntity(){
        ILPInterviewTypeDto iLPInterviewTypeTest = ILPInterviewTypeDto.mapFromEntity( iLPInterviewType1 )
        assertEquals( iLPInterviewTypeTest.id, iLPInterviewType1.id );
        assertEquals( iLPInterviewTypeTest.type, iLPInterviewType1.type);
    }
    
    @Test
    public void testMapFromILPInterviewTypesEntities(){
        List<ILPInterviewTypeDto> iLPInterviewTypesTest = ILPInterviewTypeDto.mapFromList( this.iLPInterviewTypes )
        assertEquals( iLPInterviewTypesTest[0].id, iLPInterviewType1.id );
        assertEquals( iLPInterviewTypesTest[0].type, iLPInterviewType1.type );
        assertEquals( iLPInterviewTypesTest[1].id, iLPInterviewType2.id );
        assertEquals( iLPInterviewTypesTest[1].type, iLPInterviewType2.type );
    }
    
    @Test
    public void testEquals_Same() {
        ILPInterviewTypeDto iLPInterviewTypeDto1 = new ILPInterviewTypeDto(iLPInterviewType1);
        ILPInterviewTypeDto iLPInterviewTypeDto2 = new ILPInterviewTypeDto(iLPInterviewType1);
        assertEquals(iLPInterviewTypeDto1, iLPInterviewTypeDto2)
    }
    
    @Test
    public void testEquals_Different() {
        ILPInterviewTypeDto iLPInterviewTypeDto1 = new ILPInterviewTypeDto(iLPInterviewType1);
        ILPInterviewTypeDto iLPInterviewTypeDto2 = new ILPInterviewTypeDto(iLPInterviewType2);
        assertNotEquals(iLPInterviewTypeDto1, iLPInterviewTypeDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        ILPInterviewTypeDto iLPInterviewTypeDto1 = new ILPInterviewTypeDto(iLPInterviewType1);
        ILPInterviewTypeDto iLPInterviewTypeDto2 = new ILPInterviewTypeDto(iLPInterviewType1);
        assertEquals(iLPInterviewTypeDto1.hashCode(), iLPInterviewTypeDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        ILPInterviewTypeDto iLPInterviewTypeDto1 = new ILPInterviewTypeDto(iLPInterviewType1);
        ILPInterviewTypeDto iLPInterviewTypeDto2 = new ILPInterviewTypeDto(iLPInterviewType2);
        assertNotEquals(iLPInterviewTypeDto1.hashCode(), iLPInterviewTypeDto2.hashCode())
    }
    
    @Test
    public void testConstructor_ILPInterviewType() {
        ILPInterviewTypeDto iLPInterviewTypeDto = new ILPInterviewTypeDto( iLPInterviewType1 )
        assertEquals( iLPInterviewTypeDto.type, iLPInterviewType1.type)
    }
}
