package lidija.onlinerasadnik.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lidija.onlinerasadnik.dao.NarudzbaDAO;
import lidija.onlinerasadnik.dao.ProizvodDAO;
import lidija.onlinerasadnik.entity.Proizvod;
import lidija.onlinerasadnik.form.KupacForma;
import lidija.onlinerasadnik.model.KosaricaInfo;
import lidija.onlinerasadnik.model.KupacInfo;
import lidija.onlinerasadnik.model.ProizvodiInfo;
import lidija.onlinerasadnik.pagination.PaginationResult;
import lidija.onlinerasadnik.utils.Utils;
import lidija.onlinerasadnik.validator.KupacFormaValidator;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MainController {

    @Autowired
    private NarudzbaDAO narudzbaDAO;

    @Autowired
    private ProizvodDAO proizvodDAO;

    @Autowired
    private KupacFormaValidator kupacFormaValidator;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        // Case update quantity in cart
        // (@ModelAttribute("cartForm") @Validated CartInfo cartForm)
        if (target.getClass() == KosaricaInfo.class) {

        }

        // Case save customer information.
        // (@ModelAttribute @Validated CustomerInfo customerForm)
        else if (target.getClass() == KupacForma.class) {
            dataBinder.setValidator(kupacFormaValidator);
        }

    }

    @RequestMapping("/403")
    public String accessDenied() {
        return "/403";
    }

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    // Product List
    @RequestMapping({ "/productList" })
    public String listProductHandler(Model model, //
                                     @RequestParam(value = "name", defaultValue = "") String likeName,
                                     @RequestParam(value = "stranica", defaultValue = "1") int stranica) {
        final int maxRezultat = 5;
        final int maxNavigationPage = 10;

        PaginationResult<ProizvodiInfo> result = proizvodDAO.queryProducts(stranica, //
                maxRezultat, maxNavigationPage, likeName);

        model.addAttribute("paginationProducts", result);
        return "productList";
    }

    @RequestMapping({ "/buyProduct" })
    public String listProductHandler(HttpServletRequest request, Model model, //
                                     @RequestParam(value = "sifra", defaultValue = "") String sifra) {

        Proizvod proizvod = null;
        if (sifra != null && sifra.length() > 0) {
            proizvod = proizvodDAO.nadiProizvod(sifra);
        }
        if (proizvod != null) {

            //
            KosaricaInfo kosaricaInfo = Utils.getCartInSession(request);

            ProizvodiInfo proizvodiInfo = new ProizvodiInfo(proizvod);

            kosaricaInfo.addProduct(proizvodiInfo, 1);
        }

        return "redirect:/kosarica";
    }

    @RequestMapping({ "/shoppingCartRemoveProduct" })
    public String removeProductHandler(HttpServletRequest request, Model model, //
                                       @RequestParam(value = "sifra", defaultValue = "") String sifra) {
        Proizvod proizvod = null;
        if (sifra != null && sifra.length() > 0) {
            proizvod = proizvodDAO.nadiProizvod(sifra);
        }
        if (proizvod != null) {

            KosaricaInfo kosaricaInfo = Utils.getCartInSession(request);

            ProizvodiInfo proizvodiInfo = new ProizvodiInfo(proizvod);

            kosaricaInfo.ukloniProizvod(proizvodiInfo);

        }

        return "redirect:/kosarica";
    }

    // POST: Update quantity for product in cart
    @RequestMapping(value = { "/kosarica" }, method = RequestMethod.POST)
    public String shoppingCartUpdateQty(HttpServletRequest request, //
                                        Model model, //
                                        @ModelAttribute("kosaricaForma") KosaricaInfo kosaricaForma) {

        KosaricaInfo kosaricaInfo = Utils.getCartInSession(request);
        kosaricaForma.updateKolicina(kosaricaForma);

        return "redirect:/kosarica";
    }

    // GET: Show cart.
    @RequestMapping(value = { "/kosarica" }, method = RequestMethod.GET)
    public String shoppingCartHandler(HttpServletRequest request, Model model) {
        KosaricaInfo mojaKosarica = Utils.getCartInSession(request);

        model.addAttribute("kosaricaForma", mojaKosarica);
        return "kosarica";
    }

    // GET: Enter customer information.
    @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.GET)
    public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {

        KosaricaInfo kosaricaInfo = Utils.getCartInSession(request);

        if (kosaricaInfo.jePrazan()) {

            return "redirect:/kosarica";
        }
        KupacInfo kupacInfo = kosaricaInfo.getKupacInfo();

        KupacForma kupacForma = new KupacForma();

        model.addAttribute("kupacForma", kupacForma);

        return "shoppingCartCustomer";
    }

    // POST: Save customer information.
    @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.POST)
    public String shoppingCartCustomerSave(HttpServletRequest request, //
                                           Model model, //
                                           @ModelAttribute("kupacForma") @Validated KupacForma kupacForma, //
                                           BindingResult result, //
                                           final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            kupacForma.setJeOK(false);
            // Forward to reenter customer info.
            return "shoppingCartCustomer";
        }

        kupacForma.setJeOK(true);
        KosaricaInfo kosaricaInfo = Utils.getCartInSession(request);
        KupacInfo kupacInfo = new KupacInfo(kupacForma);
        kosaricaInfo.setKupacInfo(kupacInfo);

        return "redirect:/shoppingCartConfirmation";
    }

    // GET: Show information to confirm.
    @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.GET)
    public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
        KosaricaInfo kosaricaInfo = Utils.getCartInSession(request);

        if (kosaricaInfo == null || kosaricaInfo.jePrazan()) {

            return "redirect:/kosarica";
        } else if (!kosaricaInfo.jeOkKupac()) {

            return "redirect:/shoppingCartCustomer";
        }
        model.addAttribute("mojaKosarica", kosaricaInfo);

        return "shoppingCartConfirmation";
    }

    // POST: Submit Cart (Save)
    @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)

    public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
        KosaricaInfo kosaricaInfo = Utils.getCartInSession(request);

        if (kosaricaInfo.jePrazan()) {

            return "redirect:/kosarica";
        } else if (!kosaricaInfo.jeOkKupac()) {

            return "redirect:/shoppingCartCustomer";
        }
        try {
            narudzbaDAO.spremiNarudzbu(kosaricaInfo);
        } catch (Exception e) {

            return "shoppingCartConfirmation";
        }

        // Remove Cart from Session.
        Utils.removeCartInSession(request);

        // Store last cart.
        Utils.storeLastOrderedCartInSession(request, kosaricaInfo);

        return "redirect:/shoppingCartFinalize";
    }

    @RequestMapping(value = { "/shoppingCartFinalize" }, method = RequestMethod.GET)
    public String shoppingCartFinalize(HttpServletRequest request, Model model) {

        KosaricaInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);

        if (lastOrderedCart == null) {
            return "redirect:/kosarica";
        }
        model.addAttribute("lastOrderedCart", lastOrderedCart);
        return "shoppingCartFinalize";
    }

    @RequestMapping(value = { "/slikaProizvoda" }, method = RequestMethod.GET)
    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
                             @RequestParam("sifra") String sifra) throws IOException {
        Proizvod proizvod = null;
        if (sifra != null) {
            proizvod = this.proizvodDAO.nadiProizvod(sifra);
        }
        if (proizvod != null && proizvod.getSlika() != null) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(proizvod.getSlika());
        }
        response.getOutputStream().close();
    }

}
