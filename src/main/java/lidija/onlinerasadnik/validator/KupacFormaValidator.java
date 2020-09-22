package lidija.onlinerasadnik.validator;

import org.apache.commons.validator.routines.EmailValidator;
import lidija.onlinerasadnik.form.KupacForma;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class KupacFormaValidator implements Validator {

    private EmailValidator mailValidator = EmailValidator.getInstance();


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == KupacForma.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        KupacForma kupacInfo = (KupacForma) target;

        // Check the fields of CustomerForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ime", "NotEmpty.kupacForma.ime");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mail", "NotEmpty.kupacForma.mail");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "adresa", "NotEmpty.kupacForma.adresa");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tel", "NotEmpty.kupacForma.tel");

        if (!mailValidator.isValid(kupacInfo.getMail())) {
            errors.rejectValue("mail", "Pattern.kupacForma.mail");
        }
    }

}
