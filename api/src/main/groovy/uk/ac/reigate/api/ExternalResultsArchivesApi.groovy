package uk.ac.reigate.api;

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import uk.ac.reigate.domain.academic.ExternalResultsArchive
import uk.ac.reigate.dto.ExternalResultsArchiveDto
import uk.ac.reigate.exceptions.NotFoundException
import uk.ac.reigate.services.ExternalResultsArchiveService
import uk.ac.reigate.services.student.StudentService

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE


@Controller
@RequestMapping(value = "/", produces = [ APPLICATION_JSON_VALUE ])
@Api(value = "/", description = "the externalResultsArchives API")
public class ExternalResultsArchivesApi {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalResultsArchivesApi.class);
    
    @Autowired
    private final ExternalResultsArchiveService externalResultsArchiveService;
    
    @Autowired
    private final StudentService studentService;
    
    
    /**
     * Default NoArgs constructor
     */
    ExternalResultsArchivesApi() {}
    
    /**
     * Autowired constructor
     */
    ExternalResultsArchivesApi(ExternalResultsArchiveService externalResultsArchiveService) {
        this.externalResultsArchiveService = externalResultsArchiveService;
    }
    
    /**
     * The ExternalResultsArchivesGet method is used to retrieve a full list of all the ExternalResultsArchiveDto objects
     *
     * @return A ResponseEntity with the corresponding list of ExternalResultsArchiveDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of ExternalResultsArchive entities", notes = "A GET request to the ExternalResultsArchives endpoint returns an array of all the ExternalResultsArchives in the system.", response = ExternalResultsArchiveDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of ExternalResultsArchives")
    ])
    @RequestMapping(value = "/externalResultsArchives", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<ExternalResultsArchiveDto>> getAll() throws NotFoundException {
        LOGGER.info("** ExternalResultsArchivesApi - ExternalResultsArchivesGet");
        List<ExternalResultsArchive> externalResultsArchives = externalResultsArchiveService.findAll();
        return new ResponseEntity<List<ExternalResultsArchiveDto>>(ExternalResultsArchiveDto.mapFromList(externalResultsArchives), HttpStatus.OK);
    }
    
    
    /**
     * The ExternalResultsArchivesGet method is used to retrieve a full list of students by the ExternalResultsArchiveDto objects
     *
     * @return A ResponseEntity with the corresponding list of ExternalResultsArchiveDto objects
     * @throws NotFoundException if nothing is found then the the NotFoundException is thrown.
     */
    @ApiOperation(value = "Collection of ExternalResultsArchive entities", notes = "A GET request to the ExternalResultsArchives endpoint returns an array of all the ExternalResultsArchives in the system.", response = ExternalResultsArchiveDto.class, responseContainer = "List")
    @ApiResponses(value = [
        @ApiResponse(code = 200, message = "An array of ExternalResultsArchives")
    ])
    @RequestMapping(value = "/students/{studentId}/externalResults", produces = ["application/json"], method = RequestMethod.GET)
    public ResponseEntity<List<ExternalResultsArchiveDto>> externalResultsArchiveGet(
            @ApiParam(value = "The unique ID of the Student retrieves the List of entryQualifications for the selected student", required = true)
            @PathVariable("studentId") Integer studentId
    ) throws NotFoundException {
        LOGGER.info("** ExternalResultsArchivesApi - ExternalResultsArchivesGet");
        List<ExternalResultsArchive> externalResultsArchive = externalResultsArchiveService.getByStudent(studentId);
        if (externalResultsArchive == null) {
            throw new NotFoundException();
        }
        return new ResponseEntity<List<ExternalResultsArchiveDto>>(ExternalResultsArchiveDto.mapFromList(externalResultsArchive), HttpStatus.OK);
    }
}
