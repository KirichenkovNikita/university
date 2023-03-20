package ru.systematic.university.service.validator.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.systematic.university.service.exception.FileUploadException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AvatarFileValidatorTest {
    private final MultipartFile correctFile = new MockMultipartFile("Test.png", "Test.png", "", "Test.png".getBytes());
    private final MultipartFile nullFile = null;
    private final MultipartFile emptyFile = new MockMultipartFile("Test.png", new byte[0]);
    private final MultipartFile formatFile = new MockMultipartFile("Test.csv", "Test.csv", "", "Test.csv".getBytes());
    private final AvatarFileValidator validator = new AvatarFileValidator();

    @Test
    void validateShouldDoNothingWhenFileCorrect() {
        assertDoesNotThrow(() -> {
            validator.validate(correctFile);
        });
    }

    @Test
    void validateShouldThrowExceptionWhenFileIsNull() {
        FileUploadException exception = Assertions.assertThrows(FileUploadException.class, () -> {
            validator.validate(nullFile);
        });
        assertThat(exception.getMessage()).isEqualTo("Filename empty");
    }

    @Test
    void validateShouldThrowExceptionWhenFileIsEmpty() {
        FileUploadException exception = Assertions.assertThrows(FileUploadException.class, () -> {
            validator.validate(emptyFile);
        });
        assertThat(exception.getMessage()).isEqualTo("Filename empty");
    }

    @Test
    void validateShouldThrowExceptionWhenFormatIncorrect() {
        FileUploadException exception = Assertions.assertThrows(FileUploadException.class, () -> {
            validator.validate(formatFile);
        });
        assertThat(exception.getMessage()).isEqualTo("File format incorrect");
    }
}
