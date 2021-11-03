package uk.ac.reigate.dto;

import static org.junit.Assert.*
import org.junit.Before

import org.junit.Test

import uk.ac.reigate.domain.lookup.Subject
import uk.ac.reigate.dto.lookup.SubjectDto

public class SubjectDtoTest {
    
    private Subject subject1
    
    private Subject subject2
    
    private List<Subject> subjects
    
    @Before
    public void setup() {
        subject1 = new Subject(
                id: 1,
                code:'M',
                description:'Maths'
                );
        subject2 = new Subject(
                id: 2,
                code:'S',
                description:'Science'
                );
        subjects = Arrays.asList(subject1, subject2);
    }
    
    @Test
    public void testMapFromSubjectEntity(){
        SubjectDto subjectTest = SubjectDto.mapFromEntity( subject1 )
        assertEquals( subjectTest.id, subject1.id );
        assertEquals( subjectTest.code, subject1.code);
        assertEquals( subjectTest.description, subject1.description);
    }
    
    @Test
    public void testMapFromSubjectsEntities(){
        List<SubjectDto> subjectsDtoTest = SubjectDto.mapFromList( subjects )
        assertEquals( subjectsDtoTest[0].id, subject1.id );
        assertEquals( subjectsDtoTest[0].code, subject1.code);
        assertEquals( subjectsDtoTest[0].description, subject1.description);
        assertEquals( subjectsDtoTest[1].id, subject2.id );
        assertEquals( subjectsDtoTest[1].code, subject2.code);
        assertEquals( subjectsDtoTest[1].description, subject2.description);
    }
    
    @Test
    public void testEquals_Same() {
        SubjectDto subjectDto1 = new SubjectDto(subject1)
        SubjectDto subjectDto2 = new SubjectDto(subject1)
        assertEquals(subjectDto1, subjectDto2)
    }
    
    @Test
    public void testEquals_Different() {
        SubjectDto subjectDto1 = new SubjectDto(subject1)
        SubjectDto subjectDto2 = new SubjectDto(subject2)
        assertNotEquals(subjectDto1, subjectDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        SubjectDto subjectDto1 = new SubjectDto(subject1)
        SubjectDto subjectDto2 = new SubjectDto(subject1)
        assertEquals(subjectDto1.hashCode(), subjectDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        SubjectDto subjectDto1 = new SubjectDto(subject1)
        SubjectDto subjectDto2 = new SubjectDto(subject2)
        assertNotEquals(subjectDto1.hashCode(), subjectDto2.hashCode())
    }
    
    @Test
    public void testConstructor_Subject() {
        SubjectDto subjectDto = new SubjectDto(subject1)
        assertEquals( subjectDto.code, subject1.code )
        assertEquals( subjectDto.description, subject1.description )
    }
}
