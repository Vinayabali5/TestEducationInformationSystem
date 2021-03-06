package uk.ac.reigate.services.ilp

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import uk.ac.reigate.domain.academic.AcademicYear
import uk.ac.reigate.domain.academic.Student
import uk.ac.reigate.domain.ilp.ILPInterview
import uk.ac.reigate.dto.ilp.ILPInterviewDto
import uk.ac.reigate.dto.ilp.ILPInterviewUpdateDto
import uk.ac.reigate.exceptions.InvalidDataException
import uk.ac.reigate.exceptions.InvalidOperationException
import uk.ac.reigate.repositories.ilp.ILPInterviewRepository
import uk.ac.reigate.services.AcademicYearService
import uk.ac.reigate.services.CourseGroupService
import uk.ac.reigate.services.ICoreDataService
import uk.ac.reigate.services.IDtoCreateUpdateService
import uk.ac.reigate.services.IStudentDataService
import uk.ac.reigate.services.PersonService
import uk.ac.reigate.services.StaffService
import uk.ac.reigate.services.lip.LIPService
import uk.ac.reigate.services.student.StudentService

/**
 * This service is used to manage the ILP Interview data.
 * 
 * @author Michael Horgan
 * @author Vinaya Bali
 *
 */
@Service
class ILPInterviewService implements ICoreDataService<ILPInterview, Integer>, IStudentDataService<List<ILPInterview>>, IDtoCreateUpdateService<ILPInterviewDto, ILPInterview>{
    
    @Autowired
    ILPInterviewRepository ilpInterviewRepository
    
    @Autowired
    ILPInterviewTypeService ilpInterviewTypeService;
    
    @Autowired
    StudentService studentService;
    
    @Autowired
    CourseGroupService courseGroupService;
    
    @Autowired
    StaffService staffService;
    
    @Autowired
    AcademicYearService academicYearService;
    
    @Autowired
    PersonService personService
    
    @Autowired
    LetterService letterService
    
    @Autowired
    OfficeActionService officeActionService
    
    @Autowired
    LIPService lipService
    
    /**
     * Default NoArgs constructor
     */
    ILPInterviewService() {}
    
    /**
     * Autowired Constructor
     *
     * @param ilpInterviewRepository
     */
    ILPInterviewService(ILPInterviewRepository ilpInterviewRepository, ILPInterviewTypeService ilpInterviewTypeService, StudentService studentService, CourseGroupService courseGroupService, StaffService staffService, AcademicYearService academicYearService, PersonService personService, LetterService letterService, OfficeActionService officeActionService, LIPService lipService) {
        super();
        this.ilpInterviewRepository = ilpInterviewRepository;
        this.ilpInterviewTypeService = ilpInterviewTypeService;
        this.studentService = studentService;
        this.courseGroupService= courseGroupService;
        this.staffService = staffService;
        this.academicYearService = academicYearService;
        this.personService = personService;
        this.letterService = letterService;
        this.officeActionService = officeActionService;
        this.lipService = lipService;
    }
    
    /**
     * This method is used to find an individual ILPInterview using the ILPInterview ID fields
     *
     * @param id the ID fields to search for
     * @return the ILPInterview object that matches the ID supplied, or null if not found
     */
    @Override
    @Transactional(readOnly = true)
    ILPInterview findById(Integer id) {
        return ilpInterviewRepository.findById(id).orElse(null)
    }
    
    /**
     * This method is used to find all ILPInterview objects
     * 
     * @return a List of ILPInterviews
     */
    @Override
    @Transactional(readOnly = true)
    List<ILPInterview> findAll() {
        return ilpInterviewRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    List<ILPInterview> findByCourseGroupId(Integer courseGroupId) {
        return ilpInterviewRepository.findByCourseGroup_Id(courseGroupId);
    }
    
    @Transactional(readOnly = true)
    List<ILPInterview> findByCourseAndYear(Integer courseId, AcademicYear year) {
        return ilpInterviewRepository.findByCourseGroup_IdAndAcademicYear(courseId, year)
    }
    
    /**
     * This service method is used to save a complete ILPInterview object in the database
     *
     * @param ilpInterview the new ILPInterview object to be saved
     * @return the saved version of the ILPInterview object
     */
    @Override
    @Transactional
    @PreAuthorize("@securityChecker.checkWriter(authentication) or hasRole('Staff')")
    public ILPInterview save(ILPInterview ilpInterview) {
        return ilpInterviewRepository.save(ilpInterview)
    }
    
    /**
     * This methods throws an InvalidOperationException when called. ILPInterview should not be deleted.
     */
    @Override
    public void delete(ILPInterview obj) {
        throw new InvalidOperationException("ILPInterview should not be deleted");
    }
    
    /**
     * This method is used to save a list of ILPInterview objects to the database
     *
     * @param ilpInterviews a list of ILPInterviews to be saved to the database
     * @return the list of save ILPInterview objects
     */
    @Transactional
    public List<ILPInterview> saveILPInterviews(List<ILPInterview> ilpInterviews) {
        return ilpInterviews.collect { ilpInterview -> save(ilpInterview) };
    }
    
    /** 
     * This method is used to retrieve a list of ILPInterview data objects for the supplied studentId.
     * 
     * @param studentId the studentId to use for the retrieval
     * @return List of ILPInterview by studentId
     */
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public  List<ILPInterview> getByStudentId(Integer studentId){
        return ilpInterviewRepository.findByStudent_IdAndPrivateEntry(studentId, false);
    }
    
    /**
     * This method is used to retrieve a List of ILPInterview object for the supplied Student.
     *
     * @param student the Student to use for the search.
     * @return a List of StudentReferralReason objects.
     */
    public List<ILPInterview> getByStudent(Student student) {
        return ilpInterviewRepository.findByStudent(student)
    }
    
    /** 
     * This method is used to retrieve all the students ILP interviews including those that are marked as private.
     * 
     * @param studentId the ID of the student.
     * @return List a List of ILP Interviews for the given student.
     */
    @PreAuthorize("@securityChecker.checkReader(authentication) and hasRole('ROLE_Safeguarding')")
    public  List<ILPInterview> getByStudentAll(Integer studentId){
        return ilpInterviewRepository.findByStudent_Id(studentId);
    }
    
    /**
     * This method is used to retrieve the students ILP interviews. This will retrieve different ILP Interviews depending
     * on the user permissions of the requesting user. 
     * 
     * <ul>
     * <li>Safeguarding Users - get all ILP Interviews.</li>
     * <li>Pastoral Users - get all non-safeguarding private ILP Interviews.</li>
     * <li>Staff Users - get all non private entries and any entries they have created themselves.</li>
     * </ul>
     * 
     * @param studentId the ID of the student.
     * @return a List of ILP Interviews for the given student that the current user has access to view.
     */
    //    @PreAuthorize("@securityChecker.checkReader(authentication)")
    //    public List<ILPInterview> getByStudent(Integer studentId){
    //        List<ILPInterview> ilpInterviews = new ArrayList<ILPInterview>()
    //        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //        String username = auth.getName()
    //        Person activeUser = personService.findByUsername(username)
    //        if(activeUser != null && activeUser.roles != null) {
    //            Set<String> roles = activeUser.roles.collect { return 'ROLE_' + it.roleName }
    //            boolean userIsSafeguarding = roles.asList().contains('ROLE_Safeguarding')
    //            boolean userIsSafeguardingViewer = roles.asList().contains('ROLE_Safeguarding - Viewer')
    //            boolean userIsPastoral = roles.asList().contains('ROLE_Pastoral')
    //            boolean userIsStaff = roles.asList().contains('ROLE_Staff')
    //
    //            if (userIsSafeguarding || userIsSafeguardingViewer) {
    //                // Get Safeguarding ILP Interviews
    //                List<ILPInterview> safeguardingIlpInterviews  = ilpInterviewRepository.findByStudent_Safeguarding(studentId)
    //                ilpInterviews.addAll(safeguardingIlpInterviews)
    //            }
    //            if (userIsPastoral) {
    //                // Get Pastoral ILP Interviews
    //                List<ILPInterview> pastoralIlpInterviews = ilpInterviewRepository.findByStudent_Sensitive(studentId)
    //                ilpInterviews.addAll(pastoralIlpInterviews)
    //            }
    //            Staff staff = staffService.findByPerson(activeUser)
    //            if (userIsStaff && staff.id) {
    //                // Get ILP Entry enter by logged in staff
    //                List<ILPInterview> ownIlpInterviews = ilpInterviewRepository.findByStudentAndStaff(studentId, staff.id)
    //                ilpInterviews.addAll(ownIlpInterviews)
    //            }
    //            List<ILPInterview> admissionsIlpInterviews = ilpInterviewRepository.findByStudent_Admissions(studentId)
    //            ilpInterviews.addAll(admissionsIlpInterviews)
    //            List<ILPInterview> otherIlpInterviews = ilpInterviewRepository.findByStudent_NonSensitive(studentId)
    //            ilpInterviews.addAll(otherIlpInterviews)
    //        } else {
    //            ilpInterviews = ilpInterviewRepository.findByStudent_IdAndPrivateEntry(studentId, true)
    //        }
    //
    //        return ilpInterviews.unique()
    //    }
    
    @PreAuthorize("@securityChecker.checkReader(authentication)")
    public List<ILPInterview> getByStudent(Integer studentId){
        return ilpInterviewRepository.findByStudent_Id(studentId)
    }
    
    // Get all non-private ILP entries
    
    // Check Roles
    // - Pastoral - get private entries (not safeguarding)
    // - Safeguarding - get safeguarding entries
    // - If not Safeguarding get safeguarding entries made by the member of staff
    
    /**
     * This method is used to create a new ILPInterview data object from the supplied ILPInterviewDto.
     * 
     * @param ilpInterviewDto the DTO to use for the creation
     * @return the newly create ILPInterview
     */
    @PreAuthorize("@securityChecker.checkReader(authentication) and hasRole('ROLE_Staff')")
    public ILPInterview createFromDto(ILPInterviewDto ilpInterviewDto) throws InvalidDataException {
        Student student
        if (ilpInterviewDto.studentId != null) {
            student = studentService.findById(ilpInterviewDto.studentId)
        }
        if (student != null) {
            AcademicYear academicYear
            if (ilpInterviewDto.academicYearId != null) {
                academicYear = academicYearService.findById(ilpInterviewDto.academicYearId)
            }
            if (academicYear == null) {
                academicYear = academicYearService.getCurrentAcademicYear()
            }
            ILPInterview ilpInterview = new ILPInterview()
            ilpInterview.student = student
            ilpInterview.academicYear = academicYear
            if (ilpInterviewDto.courseGroupId != null) {
                ilpInterview.courseGroup = courseGroupService.findById(ilpInterviewDto.courseGroupId)
            }
            if (ilpInterviewDto.interviewType != null) {
                ilpInterview.type = ilpInterviewTypeService.findById(ilpInterviewDto.interviewType.id)
            }
            if (ilpInterviewDto.staffId != null) {
                ilpInterview.staff = staffService.findById(ilpInterviewDto.staffId)
            }
            ilpInterview.discussion = ilpInterviewDto.discussion
            ilpInterview.interviewDate = ilpInterviewDto.interviewDate
            ilpInterview.interviewTime = ilpInterviewDto.interviewTime
            ilpInterview.target = ilpInterviewDto.target
            ilpInterview.targetByWhen = ilpInterviewDto.targetByWhen
            ilpInterview.targetByDate = ilpInterviewDto.targetByDate
            ilpInterview.referLip = ilpInterviewDto.referLip
            ilpInterview.lipReferDate= ilpInterviewDto.lipReferDate
            if (ilpInterview.type != null && ilpInterview.type.id == 10 || ilpInterview.type.id == 13) {
                ilpInterview.privateEntry = true
            } else {
                ilpInterview.privateEntry = ilpInterviewDto.privateEntry
            }
            ilpInterview.officeAction = ilpInterviewDto.officeAction != null ? officeActionService.findById(ilpInterviewDto.officeAction) : null
            ilpInterview.officeActionString = ilpInterviewDto.officeActionString
            ilpInterview.officeNotes = ilpInterviewDto.officeNotes
            ilpInterview.toFile = ilpInterviewDto.toFile
            ilpInterview.targetProgress = ilpInterviewDto.targetProgress
            ilpInterview.targetProgressDate = ilpInterviewDto.targetProgressDate
            ilpInterview.letterSent = ilpInterviewDto.letterSent
            // Save ILP Entry to set various database defined defaults
            ILPInterview savedInterview = save(ilpInterview)
            // Processes for if a letter is specified
            if (ilpInterviewDto.letter != null) {
                ilpInterviewDto.letter.interviewId = savedInterview.id
                ilpInterview.letter = letterService.createFromDto(ilpInterviewDto.letter)
                savedInterview.letter = ilpInterview.letter
                if(savedInterview.letter.id != null) {
                    savedInterview.letterSent = true
                }
                savedInterview = save(savedInterview)
            }
            // Send LIP Referral Email if required
            if (savedInterview.referLip == true) {
                lipService.sendLIPReferralEmail(savedInterview)
            }
            return savedInterview
        } else {
            throw new InvalidDataException("No student supplied for ILP Interview")
        }
    }
    
    /**
     * This method is used to update an ILPInterview based on a ILPInterviewDto
     * 
     * @param ilpInterviewDto the DTO object to use for the update
     * @return the saved ILPInterview object
     */
    public  ILPInterview updateFromDto(ILPInterviewDto ilpInterviewDto) {
        ILPInterview ilpInterview = findById(ilpInterviewDto.id)
        ilpInterview.courseGroup = ilpInterviewDto.courseGroupId != null ? courseGroupService.findById(ilpInterviewDto.courseGroupId) : null
        ilpInterview.staff = ilpInterviewDto.staffId != null ? staffService.findById(ilpInterviewDto.staffId) : null
        ilpInterview.type = ilpInterviewTypeService.findById(ilpInterviewDto.typeId)
        ilpInterview.discussion = ilpInterviewDto.discussion
        ilpInterview.interviewDate = ilpInterviewDto.interviewDate
        ilpInterview.interviewTime = ilpInterviewDto.interviewTime;
        ilpInterview.target = ilpInterviewDto.target
        ilpInterview.targetByWhen = ilpInterviewDto.targetByWhen
        ilpInterview.targetByDate = ilpInterviewDto.targetByDate
        ilpInterview.referLip = ilpInterviewDto.referLip
        ilpInterview.lipReferDate= ilpInterviewDto.lipReferDate
        ilpInterview.privateEntry = ilpInterviewDto.privateEntry
        ilpInterview.officeAction = ilpInterviewDto.officeAction != null ? officeActionService.findById(ilpInterviewDto.officeAction) : null
        ilpInterview.officeActionString = ilpInterviewDto.officeActionString
        ilpInterview.officeNotes = ilpInterviewDto.officeNotes
        ilpInterview.toFile = ilpInterviewDto.toFile
        ilpInterview.targetProgress = ilpInterviewDto.targetProgress
        ilpInterview.targetProgressDate = ilpInterviewDto.targetProgressDate
        ilpInterview.letterSent = ilpInterviewDto.letterSent
        // Save ILP Entry to set various data
        ILPInterview savedInterview = save(ilpInterview)
        if(ilpInterviewDto.letter != null) {
            if (ilpInterviewDto.letter.id == null) {
                ilpInterviewDto.letter.interviewId = savedInterview.id
                ilpInterview.letter = letterService.createFromDto(ilpInterviewDto.letter)
            } else {
                ilpInterview.letter = letterService.updateFromDto(ilpInterviewDto.letter)
            }
        }
        return save(ilpInterview)
    }
    
    /**
     * This method is used to update an ILPInterview based on an ILPInterviewUpdateDto. 
     *
     * @param ilpInterviewDto the DTO object to use for the update
     * @return the saved ILPInterview object
     */
    public  ILPInterview updateFromDto(ILPInterviewUpdateDto ilpInterviewDto) {
        ILPInterview ilpInterview = findById(ilpInterviewDto.id)
        ilpInterview.staff = ilpInterviewDto.staffId != null ? staffService.findById(ilpInterviewDto.staffId) : null
        ilpInterview.type = ilpInterviewTypeService.findById(ilpInterviewDto.typeId)
        ilpInterview.discussion = ilpInterviewDto.discussion
        ilpInterview.interviewDate = ilpInterviewDto.interviewDate
        ilpInterview.interviewTime = ilpInterviewDto.interviewTime;
        ilpInterview.target = ilpInterviewDto.target
        ilpInterview.targetByWhen = ilpInterviewDto.targetByWhen
        ilpInterview.targetByDate = ilpInterviewDto.targetByDate
        ilpInterview.referLip = ilpInterviewDto.referLip
        ilpInterview.lipReferDate= ilpInterviewDto.lipReferDate
        ilpInterview.privateEntry = ilpInterviewDto.privateEntry
        ilpInterview.officeActionString = ilpInterviewDto.officeActionString
        ilpInterview.officeNotes = ilpInterviewDto.officeNotes
        ilpInterview.toFile = ilpInterviewDto.toFile
        ilpInterview.targetProgress = ilpInterviewDto.targetProgress
        ilpInterview.targetProgressDate = ilpInterviewDto.targetProgressDate
        ilpInterview.letterSent = ilpInterviewDto.letterSent
        return save(ilpInterview)
    }
    
    public void delete(Integer id) {
        ilpInterviewRepository.deleteById(id)
    }
    
}
