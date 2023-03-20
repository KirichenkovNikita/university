package ru.systematic.university.service.validator.user;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import ru.systematic.university.service.exception.FileUploadException;
import ru.systematic.university.service.validator.EntityValidator;
import ru.systematic.university.service.validator.anotation.Validator;

import java.util.Arrays;

@Validator
public class AvatarFileValidator implements EntityValidator<MultipartFile> {
    private static final String[] FILE_FORMATS = {"png", "jpeg"};
    private static final int FILE_SIZE = 5000000;
    private static final Logger LOGGER = LoggerFactory.getLogger(AvatarFileValidator.class);

    @Override
    public void validate(MultipartFile entity) {
        if (entity == null || entity.getOriginalFilename().isEmpty()) {
            LOGGER.error("Filename empty");
            throw new FileUploadException("Filename empty");
        }

        String extension = FilenameUtils.getExtension(entity.getOriginalFilename());
        if (!Arrays.asList(FILE_FORMATS).contains(extension)) {
            LOGGER.error("File format incorrect");
            throw new FileUploadException("File format incorrect");
        }

        if (entity.getSize() > FILE_SIZE) {
            LOGGER.error("File size exceeds limit");
            throw new FileUploadException("File size exceeds limit");
        }
    }
}
