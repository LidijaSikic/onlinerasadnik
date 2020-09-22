package lidija.onlinerasadnik.controller;

import java.util.List;



import org.apache.commons.lang.exception.ExceptionUtils;
import lidija.onlinerasadnik.dao.NarudzbaDAO;
import lidija.onlinerasadnik.dao.ProizvodDAO;
import lidija.onlinerasadnik.entity.Proizvod;
import lidija.onlinerasadnik.form.ProizvodForma;
import lidija.onlinerasadnik.model.DetaljiNarudzbeInfo;
import lidija.onlinerasadnik.model.NarudzbaInfo;
import lidija.onlinerasadnik.pagination.PaginationResult;
import lidija.onlinerasadnik.validator.ProizvodFormaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Transactional
public class AdminController {

    @Autowired
    private NarudzbaDAO narudzbaDAO;

    @Autowired
    private ProizvodDAO proizvodDAO;

    @Autowired
    private ProizvodFormaValidator proizvodFormaValidator;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == ProizvodForma.class) {
            dataBinder.setValidator(proizvodFormaValidator);
        }
    }

    // GET: Show Login Page
    @RequestMapping(value = { "/admin/login" }, method = RequestMethod.GET)
    public String login(Model model) {

        return "login";
    }

    @RequestMapping(value = { "/admin/accountInfo" }, method = RequestMethod.GET)
    public String accountInfo(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getPassword());
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.isEnabled());

        model.addAttribute("userDetails", userDetails);
        return "accountInfo";
    }

    @RequestMapping(value = { "/admin/orderList" }, method = RequestMethod.GET)
    public String orderList(Model model, //
                            @RequestParam(value = "page", defaultValue = "1") String pageStr) {
        int page = 1;
        try {
            page = Integer.parseInt(pageStr);
        } catch (Exception e) {
        }
        final int MAX_RESULT = 5;
        final int MAX_NAVIGATION_PAGE = 10;

        PaginationResult<NarudzbaInfo> paginationResult //
                = narudzbaDAO.listaNarudzbiInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);

        model.addAttribute("paginationResult", paginationResult);
        return "orderList";
    }

    // GET: Show product.
    @RequestMapping(value = { "/admin/proizvod" }, method = RequestMethod.GET)
    public String product(Model model, @RequestParam(value = "sifra", defaultValue = "") String sifra) {
        ProizvodForma proizvodForma = null;

        if (sifra != null && sifra.length() > 0) {
            Proizvod proizvod = proizvodDAO.nadiProizvod(sifra);
            if (proizvod != null) {
                proizvodForma = new ProizvodForma(proizvod);
            }
        }
        if (proizvodForma == null) {
            proizvodForma = new ProizvodForma();
            proizvodForma.setNoviProizvod(true);
        }
        model.addAttribute("proizvodForma", proizvodForma);
        return "proizvod";
    }

    // POST: Save product
    @RequestMapping(value = { "/admin/proizvod" }, method = RequestMethod.POST)
    public String productSave(Model model, //
                              @ModelAttribute("proizvodForma") @Validated ProizvodForma proizvodForma, //
                              BindingResult result, //
                              final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "proizvod";
        }
        try {
            proizvodDAO.save(proizvodForma);
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = rootCause.getMessage();
            model.addAttribute("errorMessage", message);

            return "proizvod";
        }

        return "redirect:/listaProizvoda";
    }

    @RequestMapping(value = { "/admin/narudzba" }, method = RequestMethod.GET)
    public String orderView(Model model, @RequestParam("Id") String Id) {
        NarudzbaInfo narudzbaInfo = null;
        if (narudzbaInfo != null) {
            narudzbaInfo = this.narudzbaDAO.nadiDetaljeNarudzbe(Id);
        }
        if (narudzbaInfo == null) {
            return "redirect:/admin/orderList";
        }
        List<DetaljiNarudzbeInfo> detalji = this.narudzbaDAO.listaDetaljiNarudzbeInfo(Id);
        narudzbaInfo.setDetalji(detalji);

        model.addAttribute("narudzbaInfo", narudzbaInfo);

        return "narudzba";
    }

}
