package makbn;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MainPathFactory {



    public static ArrayList<DirectedPath> mainPath(String filePath) throws IOException {
        ArrayList<DirectedPath> directedPaths=new ArrayList<DirectedPath>();
        InputStream ExcelFileToRead = new FileInputStream(filePath);
        HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
        HSSFSheet sheet=wb.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;
        Iterator rows = sheet.rowIterator();

        for(int rowIndex=0;rows.hasNext();rowIndex++){
            row=(HSSFRow) rows.next();
            if(rowIndex>=4) {
                Iterator cells = row.cellIterator();
                String first=null,last=null;
                ArrayList<String> middle=new ArrayList<String>();
                for (int colIndex=0;cells.hasNext();colIndex++) {
                    cell=(HSSFCell) cells.next();
                    if(colIndex==1 || colIndex==5) {
                        if(cell.getStringCellValue()==null || cell.getStringCellValue().isEmpty())
                            continue;
                        String[] cities=cell.getStringCellValue().trim().split("-");
                        if(colIndex==1) {
                            first = cities[0].trim();
                            last = cities[1].trim();
                        }else
                            for(int i=0;i<cities.length;i++)
                                middle.add(cities[i].trim());
                    }
                }
                if(first==null)
                    continue;
                ArrayList<LocationVertex> locationVertices=new ArrayList<LocationVertex>();
                ArrayList<String> pNames=new ArrayList<String>();
                pNames.add(first);
                for(int i=middle.size()-1;i>=0;i--)
                    pNames.add(middle.get(i));
                pNames.add(last);
                for(String s:pNames){
                    LocationVertex vertex=Utils.findLocationVertexByName(s);
                    if(vertex!=null)
                        locationVertices.add(vertex);
                }
                DirectedPath directedPath=new DirectedPath(locationVertices);
                directedPath.setColor(Color.black);
                directedPath.setStroke(new BasicStroke(2));
                directedPaths.add(directedPath);
            }
        }
        return directedPaths;

    }

    public static HashMap<DirectedPath.PathType,ArrayList<DirectedPath>> airAndRailWay(String filePath) throws IOException {
        HashMap<DirectedPath.PathType,ArrayList<DirectedPath>> directedPaths=new HashMap<DirectedPath.PathType, ArrayList<DirectedPath>>();
        directedPaths.put(DirectedPath.PathType.railway,new ArrayList<DirectedPath>());
        directedPaths.put(DirectedPath.PathType.airline,new ArrayList<DirectedPath>());
        InputStream ExcelFileToRead = new FileInputStream(filePath);
        HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
        HSSFSheet sheet=wb.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;
        Iterator rows = sheet.rowIterator();
        for( int rowIndex=0;rows.hasNext(); rowIndex++) {
            row=(HSSFRow) rows.next();
            if(rowIndex>=1) {
                Iterator cells = row.cellIterator();
                String src=null;
                String dst=null;
                String[] airlineName=null;
                boolean isAirline=false;
                boolean isRailway=false;
                for (int colIndex=0;cells.hasNext(); colIndex++) {
                    cell=(HSSFCell) cells.next();
                    if(colIndex==1) {
                        String[] cities=cell.getStringCellValue().trim().split("-");
                        src = cities[0].trim();
                        dst = cities[1].trim();
                    }else if(colIndex==3) {
                        int value = (int) cell.getNumericCellValue();
                        isRailway = value == 1;
                    }else if(colIndex==4) {
                        int value = (int) cell.getNumericCellValue();
                        isAirline= value==1;
                    }else if(colIndex==5){
                        airlineName=cell.getStringCellValue().trim().split("-");
                    }
                }
                if(src==null || dst==null)
                    continue;
                ArrayList<LocationVertex> locationVertices=new ArrayList<LocationVertex>();

                ArrayList<String> pNames=new ArrayList<String>();
                pNames.add(src);
                pNames.add(dst);

                for(String s:pNames){
                    LocationVertex vertex=Utils.findLocationVertexByName(s);
                    if(vertex!=null)
                        locationVertices.add(vertex);
                }
                if(isRailway) {
                    directedPaths.get(DirectedPath.PathType.railway).add(createDirectedPath(locationVertices,
                            new BasicStroke(4),
                            new Color(185,122,87,160),
                            DirectedPath.PathType.railway,
                            null));
                }
                if(isAirline){
                    for(String airline:airlineName){
                        Color color=null;
                        BasicStroke stroke=null;
                        if (airline.equals("iran air")) {
                            color =  new Color(0, 255, 255,160);
                            stroke=new BasicStroke(5);
                        } else if (airline.equals("ata")) {
                            color = new Color(255, 0, 255,160);
                            stroke=new BasicStroke(4);
                        } else if (airline.equals("mahan")) {
                            color = new Color(255, 0, 0,160);
                            stroke=new BasicStroke(3);
                        } else if (airline.equals("taban")) {
                            color = new Color(0, 255, 0,160);
                            stroke=new BasicStroke(2);
                        }

                        directedPaths.get(DirectedPath.PathType.airline).add(createDirectedPath(locationVertices,
                                stroke,
                                color,
                                DirectedPath.PathType.airline,
                                airline));
                    }
                }
                rowIndex++;
            }

        }
        return directedPaths;
    }


    private static DirectedPath createDirectedPath(ArrayList<LocationVertex> locationVertices ,BasicStroke stroke, Color color, DirectedPath.PathType type,String name){
        DirectedPath directedPath=new DirectedPath(locationVertices);
        directedPath.setType(type);
        directedPath.setColor(color);
        directedPath.setStroke(stroke);
        directedPath.setAdditionalName(name);
        return directedPath;
    }

}
