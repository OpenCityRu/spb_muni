import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Use for input KML file which contains several polygons (placemarks). Output files (in the same directory) will contain one file for each <placemark>.
 * all.kml - contains polygons for all municipalities of St.Petersburg. As output files - each municipality in separate files.
 * Also full coords (like 30.034513059432943) will be decreased for size decreasing (30.03451).
 * 
 * @author inxaoc
 *
 */
public class KMLCoder {

	public static void main(String[] args) {

		BufferedReader file;
		String newStr = "";
		try {
			file = new BufferedReader(new FileReader("all.kml"));

			for (int i = 0; file.ready(); i++) {
				String str = file.readLine();
				if (str.length() > "<Placemark".length()
						&& str.substring(0, "<Placemark".length()).equals(
								"<Placemark")) {

					String[] s2 = str.split("<coordinates>");
//					System.out.println(s2[0]);
					String[] s3 = s2[1].split("</coordinates>");
					String coords = s3[0];

					String[] mas = coords.split(" ");
					String newCoords = "";

					for (String s : mas) {
						String[] latLng = s.split(",");
						String[] lat = latLng[0].split("\\.");
						newCoords += lat[0] + "." + lat[1].substring(0, 5)
								+ ",";
						String[] lng = latLng[1].split("\\.");
						newCoords += lng[0] + "." + lng[1].substring(0, 5)
								+ " ";
					}

					newStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n"
							+ s2[0]
							+ "\n<coordinates>"
							+ newCoords
							+ "</coordinates>\n" + s3[1] + "\n</kml>";

					String filename = s2[0].split("<Placemark id=\"")[1]
							.split("\"")[0] + ".kml";

					File ff = new File(filename);
					if (ff.exists()) {
						System.out
								.println("Already there is file with this name "
										+ filename);
					} else {
						BufferedWriter wr = new BufferedWriter(new FileWriter(
								ff));

						wr.write(newStr);
						wr.close();
						System.out.println("Saved file " + filename);
					}
				}
			}

			file.close();

			System.out.print("Finished.");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
