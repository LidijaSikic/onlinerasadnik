package lidija.onlinerasadnik.validator;

import lidija.onlinerasadnik.dao.ProizvodDAO;
import lidija.onlinerasadnik.entity.Proizvod;
import lidija.onlinerasadnik.form.ProizvodForma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public
class ProizvodFormaValidator implements Validator {

    @Autowired
    private ProizvodDAO proizvodDAO;


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == ProizvodForma.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProizvodForma proizvodForma = (ProizvodForma) target;

        // Check the fields of ProductForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sifra", "NotEmpty.proizvodForma.sifra");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "naziv", "NotEmpty.proizvodForma.naziv");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cijena", "NotEmpty.proizvodForma.cijena");

        String sifra = proizvodForma.getSifra();
        if (sifra != null && sifra.length() > 0) {
            if (sifra.matches("\\s+")) {
                errors.rejectValue("sifra", "Pattern.proizvodForma.sifra");
            } else if (proizvodForma.jeNoviProizvod()) {
                Proizvod proizvod = proizvodDAO.nadiProizvod(sifra);
                if (proizvod != null) {
                    errors.rejectValue("sifra", "Duplicate.proizvodForma.sifra");
                }
            }
        }
    }

}
