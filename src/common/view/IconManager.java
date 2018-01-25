package common.view;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import common.base.Logger;

/**
 * Class to handle icons for buttons, tabs, etc.
 * Icons are stored in a register and will be disposed when application ends.
 * 
 * @author nicz
 *
 */
public class IconManager {

	private static final Logger log = new Logger("IconManager", false);

	/**
	 * The location of the icon image files.
	 */
	private static final String iconPath = "/home/nicz/prog/java/workspace/icons/";

	private static Display display;
	
	private static Map<String, Image> mapNamedIcons;
	private static Map<Color,  Image> mapColorIcons;

	/**
	 * Initialize the {@link Display} on which to create images.
	 * 
	 * @param display the display to use
	 */
	public static void init(Display display) {
		IconManager.display = display;
		mapNamedIcons = new HashMap<String, Image>();
		mapColorIcons = new HashMap<Color,  Image>();
	}
	
	/**
	 * Get the icon as an image.
	 * 
	 * @param iconName the name of the icon to create, without extension.
	 * @return the created image.
	 */
	public static Image getIcon(String iconName) {
		if (mapNamedIcons.containsKey(iconName)) {
			return mapNamedIcons.get(iconName);
		} else {
			log.info("Creating new named icon for " + iconName);
			String fileName = iconPath + iconName + ".png";
			
//			URL url = IconManager.class.getResource("/icons/" + iconName + ".png");
//			String fileName = url.getFile();
//			log.info("Icon is at " + fileName);

			File fileIcon = new File(fileName);

			Image icon = null;
			if (fileIcon.exists()) {
				icon = new Image(display, fileName);
				mapNamedIcons.put(iconName, icon);
			} else {
				log.error("Could not find icon image " + fileName);
				return createDefaultIcon(16);
			}
			return icon;
		}
	}
	
	/**
	 * Creates a blank icon image of the given size.
	 * Useful in case an icon file is not found.
	 * 
	 * @param size the image size in pixels
	 * @return the created blank icon
	 */
	public static Image createDefaultIcon(int size) {
		return createColorIcon(size, display.getSystemColor(SWT.COLOR_BLACK));
	}
	
	public static Image createColorIcon(int size, Color color) {
		if (mapColorIcons.containsKey(color)) {
			log.debug("Reusing mapped color icon for " + color.toString());
			return mapColorIcons.get(color);
		} else {
			log.debug("Creating new color icon for " + color.toString());
			Image icon = new Image(display, size, size);
			Color darker = darkenColor(color, 0.7);
			GC gc = new GC(icon);
			gc.setBackground(color); 
			gc.setForeground(darker); 
			gc.fillRectangle(0, 0, size-1, size-1);
			gc.drawRectangle(0, 0, size-1, size-1);
			gc.dispose();
			darker.dispose();
			mapColorIcons.put(color, icon);
			return icon;
		}
	}
	
	public static Color darkenColor(Color color, double factor) {
		Color darker = new Color(display, 
			(int) (factor*(double) color.getRed()),
			(int) (factor*(double) color.getGreen()),
			(int) (factor*(double) color.getBlue())
		);
		return darker;
	}
	
	/**
	 * Dispose all created icon images.
	 */
	public static void disposeIcons() {
		log.info("Disposing " + mapNamedIcons.size() + " named icons");
		for (Image img : mapNamedIcons.values()) 
			if (!img.isDisposed())
				img.dispose();
		mapNamedIcons.clear();
		
		log.info("Disposing " + mapColorIcons.size() + " color icons");
		for (Color color : mapColorIcons.keySet()) {
			mapColorIcons.get(color).dispose();
			if (!color.isDisposed())
				color.dispose();
		}
		for (Image img : mapColorIcons.values()) 
			if (!img.isDisposed())
				img.dispose();
		
		mapColorIcons.clear();
	}

	
}
