/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

import java.sql.SQLException;
import utils.LogUtil;

/**
 *
 * @author AKrot
 */
public class UserCRUD {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        LogUtil.info("Appication started.");

        new Console().start();
    }
}
