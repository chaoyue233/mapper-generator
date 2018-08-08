//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
//import java.net.InetAddress;
//
///**
// * Created by jiangwulin on 16/7/29.
// */
//@RestController
//public class IpController {
//    private IpLocation[] ipLocationCollection = null;
//
//    @RequestMapping(value = "/ip", method = RequestMethod.GET)
//    @ResponseBody
//    public String getIpLocation(HttpServletRequest request) throws UnsupportedEncodingException {
//        String ip = request.getParameter("ip");
//        IpLocation ipLocation = getIpLocation(ip);
//        return ip + "|" + ipLocation.getAddress();
//        //return ip + "|" + new String(ipLocation.getAddress().getBytes("UTF-8"),"ISO-8859-1");
//    }
//
//    private IpLocation getIpLocation(String ipStr){
//        if(ipLocationCollection == null) {
//            try {
//                ClassLoader classLoader = getClass().getClassLoader();
//
//                File file = new File(classLoader.getResource("IpDetails.txt").getFile());
//                if (file.isFile() && file.exists()) { //判断文件是否存在
//                    InputStreamReader read = new InputStreamReader(
//                            new FileInputStream(file), "UTF-8");//考虑到编码格式
//                    BufferedReader bufferedReader = new BufferedReader(read);
//                    String lineTxt = null;
//                    int lineCount = Integer.parseInt(bufferedReader.readLine());
//                    ipLocationCollection = new IpLocation[lineCount];
//                    int index = 0;
//                    while ((lineTxt = bufferedReader.readLine()) != null) {
//                        String[] lines = lineTxt.split("\t");
//                        if (lines.length == 7) {
//                            IpLocation ipLocation = new IpLocation();
//                            ipLocation.setAddress(lines[6]);
//                            ipLocation.setBegin(Long.parseLong(lines[0]));
//                            ipLocation.setEnd(Long.parseLong(lines[1]));
//                            ipLocationCollection[index++] = ipLocation;
//                        }
//                    }
//                    read.close();
//                } else {
//                    System.out.println("找不到指定的文件");
//                }
//            } catch (Exception e) {
//                System.out.println("读取文件内容出错");
//                e.printStackTrace();
//            }
//        }
//
//        long ip = ipToLong(ipStr);
//
//        int length = ipLocationCollection.length;
//        int begin = 0; int end = 0; int m = 0;
//        for (begin = 0, end = length - 1; begin < end; )
//        {
//            m = (begin + end) / 2;
//
//            int r = compareIp(ipLocationCollection[m].getBegin(), ip);
//            if (r < 0)
//                begin = m + 1;
//            else if (r > 0)
//            {
//                end = m - 1;
//            }
//            else
//            {
//                return ipLocationCollection[m];
//            }
//        }
//
//        if ((begin >= 0) && (begin <= ipLocationCollection.length - 1) &&
//                containIp(ipLocationCollection[begin].getBegin(), ipLocationCollection[begin].getEnd(), ip))
//            return ipLocationCollection[begin];
//        else if ((begin >= 1) && (begin <= ipLocationCollection.length) &&
//                containIp(ipLocationCollection[begin - 1].getBegin(), ipLocationCollection[begin - 1].getEnd(), ip))
//            return ipLocationCollection[begin - 1];
//        else if ((begin >= -1) && (begin <= ipLocationCollection.length - 2) &&
//                containIp(ipLocationCollection[begin + 1].getBegin(), ipLocationCollection[begin + 1].getEnd(), ip))
//            return ipLocationCollection[begin + 1];
//
//        return new IpLocation();
//
//    }
//
//    public static long ipToLong(String ipStr)
//    {
//        try {
//            InetAddress ip = InetAddress.getByName(ipStr);
//
//            if (ip == null)
//                return 0;
//            byte[] bytes = ip.getAddress();
//            long[] longs = new long[4];
//            for(int i=0;i<4;i++){
//                longs[i] = (long)bytes[i];
//                if(longs[i] < 0){
//                    longs[i] = longs[i] + 256;
//                }
//            }
//            return (longs[3] << 24) | (longs[2] << 16)
//                    | (longs[1] << 8) | longs[0];
//        }
//        catch (Exception e){}
//        return 0;
//
//    }
//
//    private boolean containIp(long begin, long end, long ip)
//    {
//        return (compareIp(begin, ip) <= 0 && compareIp(end, ip) >= 0);
//    }
//
//    private int compareIp(long ip1, long ip2)
//    {
//        byte[] ipAddr1 = longToIpBytes(ip1);
//        byte[] ipAddr2 = longToIpBytes(ip2);
//
//        return compareIp(ipAddr1, ipAddr2);
//    }
//
//    private int compareIp(byte[] ip, byte[] beginIP)
//    {
//        for (int i = 0; i < 4; i++)
//        {
//            int r = compareByte(ip[i], beginIP[i]);
//            if (r != 0)
//                return r;
//        }
//        return 0;
//    }
//
//    private int compareByte(byte bsrc, byte bdst)
//    {
//        if ((bsrc & 0xFF) > (bdst & 0xFF))
//            return 1;
//        else if ((bsrc ^ bdst) == 0)
//            return 0;
//        else
//            return -1;
//    }
//
//    private static byte[] longToIpBytes(long ip)
//    {
//        byte[] bytes = new byte[4];
//        bytes[0] = (byte)(ip % 256);
//        bytes[1] = (byte)((ip >> 8) % 256);
//        bytes[2] = (byte)((ip >> 16) % 256);
//        bytes[3] = (byte)((ip >> 24) % 256);
//
//        return bytes;
//    }
//}