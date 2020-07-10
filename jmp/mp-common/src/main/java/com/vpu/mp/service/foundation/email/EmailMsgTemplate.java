package com.vpu.mp.service.foundation.email;

public class EmailMsgTemplate {


    public static class TableTemplate{

        public static String table = "    <table style=\'width:100%%;border-collapse: collapse;text-align: center;\' border=\' 1px solid black\'> \n" +
            "%s"+
            "    </table> ";
        public static String th = " <th> \n" +
            "%s"+
            "        </th>";
        public static String tr = " <tr> \n" +
            "%s"+
            "        </tr>";
        public static String td = " <td> \n" +
            "%s"+
            "        </td>";

    }



}
