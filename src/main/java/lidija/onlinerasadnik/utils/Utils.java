package lidija.onlinerasadnik.utils;

import javax.servlet.http.HttpServletRequest;

import lidija.onlinerasadnik.model.KosaricaInfo;

public class Utils {

    // Products in the cart, stored in Session.
    public static KosaricaInfo getCartInSession(HttpServletRequest request) {

        KosaricaInfo kosaricaInfo = (KosaricaInfo) request.getSession().getAttribute("myCart");


        if (kosaricaInfo == null) {
            kosaricaInfo = new KosaricaInfo();

            request.getSession().setAttribute("myCart", kosaricaInfo);
        }

        return kosaricaInfo;
    }

    public static void removeCartInSession(HttpServletRequest request) {
        request.getSession().removeAttribute("myCart");
    }

    public static void storeLastOrderedCartInSession(HttpServletRequest request, KosaricaInfo cartInfo) {
        request.getSession().setAttribute("lastOrderedCart", cartInfo);
    }

    public static KosaricaInfo getLastOrderedCartInSession(HttpServletRequest request) {
        return (KosaricaInfo) request.getSession().getAttribute("lastOrderedCart");
    }

}
