package uk.ac.reigate.dto;

import org.junit.Before
import org.junit.Test

import uk.ac.reigate.domain.lookup.AttendanceMonitoring

import static org.junit.Assert.*

public class AttendanceMonitoringDtoTest {
    
    private AttendanceMonitoring attendanceMonitoring1
    
    private AttendanceMonitoring attendanceMonitoring2
    
    private List<AttendanceMonitoring> attendanceMonitorings
    
    @Before
    public void setup() {
        this.attendanceMonitoring1 = new AttendanceMonitoring(
                id: 1,
                code:'G',
                description:'Good',
                warningColour: 'red'
                )
        this.attendanceMonitoring2 = new AttendanceMonitoring(
                id: 2,
                code:'B',
                description:'Bad',
                warningColour: 'red'
                )
        this.attendanceMonitorings = Arrays.asList(attendanceMonitoring1, attendanceMonitoring2)
    }
    
    @Test
    void testConstructor_attendanceMonitoring() {
        AttendanceMonitoringDto attendanceMonitoringTest = new AttendanceMonitoringDto(attendanceMonitoring1)
        assertEquals(attendanceMonitoringTest.id, attendanceMonitoring1.id)
        assertEquals(attendanceMonitoringTest.code, attendanceMonitoring1.code)
        assertEquals(attendanceMonitoringTest.description, attendanceMonitoring1.description)
        assertEquals(attendanceMonitoringTest.warningColour, attendanceMonitoring1.warningColour)
    }
    
    @Test
    public void testConstructor_nullAttendanceMonitoring() {
        AttendanceMonitoring attendanceMonitoring = null
        AttendanceMonitoringDto attendanceMonitoringDto = new AttendanceMonitoringDto(attendanceMonitoring)
        assertEquals( attendanceMonitoring, null)
    }
    
    @Test
    public void testMapFromEntity(){
        AttendanceMonitoringDto attendanceMonitoringTest = AttendanceMonitoringDto.mapFromEntity(attendanceMonitoring1)
        assertEquals(attendanceMonitoringTest.id, attendanceMonitoring1.id)
        assertEquals(attendanceMonitoringTest.code, attendanceMonitoring1.code)
        assertEquals(attendanceMonitoringTest.description, attendanceMonitoring1.description)
        assertEquals(attendanceMonitoringTest.warningColour, attendanceMonitoring1.warningColour)
    }
    
    @Test
    public void testMapFromList(){
        List<AttendanceMonitoringDto> attendanceMonitoringsDtoTest = AttendanceMonitoringDto.mapFromList(attendanceMonitorings)
        assertEquals(attendanceMonitoringsDtoTest[0].id, attendanceMonitoring1.id)
        assertEquals(attendanceMonitoringsDtoTest[0].code, attendanceMonitoring1.code)
        assertEquals(attendanceMonitoringsDtoTest[0].description, attendanceMonitoring1.description)
        assertEquals(attendanceMonitoringsDtoTest[1].id, attendanceMonitoring2.id)
        assertEquals(attendanceMonitoringsDtoTest[1].code, attendanceMonitoring2.code)
        assertEquals(attendanceMonitoringsDtoTest[1].description, attendanceMonitoring2.description)
    }
    
    @Test
    public void testEquals_Same() {
        AttendanceMonitoringDto attendanceMonitoringDto1 = new AttendanceMonitoringDto(attendanceMonitoring1)
        AttendanceMonitoringDto attendanceMonitoringDto2 = new AttendanceMonitoringDto(attendanceMonitoring1)
        assertEquals(attendanceMonitoringDto1, attendanceMonitoringDto2)
    }
    
    @Test
    public void testEquals_Different() {
        AttendanceMonitoringDto attendanceMonitoringDto1 = new AttendanceMonitoringDto(attendanceMonitoring1)
        AttendanceMonitoringDto attendanceMonitoringDto2 = new AttendanceMonitoringDto(attendanceMonitoring2)
        assertNotEquals(attendanceMonitoringDto1, attendanceMonitoringDto2)
    }
    
    @Test
    public void testHashCode_Same() {
        AttendanceMonitoringDto attendanceMonitoringDto1 = new AttendanceMonitoringDto(attendanceMonitoring1)
        AttendanceMonitoringDto attendanceMonitoringDto2 = new AttendanceMonitoringDto(attendanceMonitoring1)
        assertEquals(attendanceMonitoringDto1.hashCode(), attendanceMonitoringDto2.hashCode())
    }
    
    @Test
    public void testHashCode_Different() {
        AttendanceMonitoringDto attendanceMonitoringDto1 = new AttendanceMonitoringDto(attendanceMonitoring1)
        AttendanceMonitoringDto attendanceMonitoringDto2 = new AttendanceMonitoringDto(attendanceMonitoring2)
        assertNotEquals(attendanceMonitoringDto1.hashCode(), attendanceMonitoringDto2.hashCode())
    }
}
