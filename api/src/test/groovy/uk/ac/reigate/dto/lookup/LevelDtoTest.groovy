package uk.ac.reigate.dto.lookup;

import static org.junit.Assert.*
import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.lookup.Level
import uk.ac.reigate.domain.lookup.PossibleGradeSet
import uk.ac.reigate.dto.lookup.LevelDto



public class LevelDtoTest {
    
    private PossibleGradeSet possibleGradeSet
    
    private Level level1
    
    private Level level2
    
    private List<Level> levels
    
    @Before
    public void setup() {
        this.possibleGradeSet = new PossibleGradeSet()
        level1 =  new Level(
                id: 1,
                code:'A',
                description:'A Level',
                possibleGradeSet: possibleGradeSet,
                progressionTo: 1,
                alisQualCode: 'A'
                );
        level2 =  new Level(
                id: 1,
                code:'B',
                description:'AS Level',
                possibleGradeSet:possibleGradeSet,
                progressionTo: 1,
                alisQualCode: 'A'
                );
        levels = Arrays.asList(level1, level2);
    }
    
    @Test
    public void testMapFromLevelEntity(){
        LevelDto levelTest = LevelDto.mapFromEntity( level1 )
        assertEquals( levelTest.id, level1.id );
        assertEquals( levelTest.code, level1.code);
        assertEquals( levelTest.description, level1.description);
    }
    
    @Test
    public void testMapFromLevelsEntities(){
        List<LevelDto> levelsTest = LevelDto.mapFromList( levels )
        assertEquals( levelsTest[0].id, level1.id );
        assertEquals( levelsTest[0].code, level1.code);
        assertEquals( levelsTest[0].description, level1.description);
        assertEquals( levelsTest[1].id, level2.id );
        assertEquals( levelsTest[1].code, level2.code);
        assertEquals( levelsTest[1].description, level2.description);
    }
    
    @Test
    public void testEquals_Same() {
        LevelDto levelDto1 = new LevelDto(level1)
        LevelDto levelDto2 = new LevelDto(level1)
        assertEquals(levelDto1, levelDto2)
    }
    
    @Test
    public void testEquals_Different() {
        LevelDto levelDto1 = new LevelDto(level1)
        LevelDto levelDto2 = new LevelDto(level2)
        assertNotEquals(levelDto1, levelDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        LevelDto levelDto1 = new LevelDto(level1)
        LevelDto levelDto2 = new LevelDto(level1)
        assertEquals(levelDto1.hashCode(), levelDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        LevelDto levelDto1 = new LevelDto(level1)
        LevelDto levelDto2 = new LevelDto(level2)
        assertNotEquals(levelDto1.hashCode(), levelDto2.hashCode())
    }
    
    @Test
    public void testConstructor_Level() {
        LevelDto levelDto = new LevelDto(level1)
        assertEquals( levelDto.code, level1.code )
        assertEquals( levelDto.description, level1.description )
    }
    
    @Test
    public void testConstructor_NullLevel() {
        Level level = null
        LevelDto levelDto = new LevelDto(level)
        assertEquals( level, null )
    }
}
