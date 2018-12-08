package game.frontend;

import game.backend.cell.Cell;
import game.backend.element.*;
import javafx.scene.Group;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class ImageManager {

	private static final String IMAGE_PATH = "images/";
	private Map<String, Image> images;

	public ImageManager() {
		WrappedCandy wc = new WrappedCandy();
		VerticalStripedCandy vc = new VerticalStripedCandy();
		HorizontalStripedCandy hc = new HorizontalStripedCandy();
		images = new HashMap<>();
		images.put(new Nothing().getKey(), new Image(IMAGE_PATH + "nothing.png"));
		images.put(new Bomb().getKey(),  new Image(IMAGE_PATH + "bomb.png"));
		images.put(new Wall().getKey(),  new Image(IMAGE_PATH + "wall.png"));
		images.put(new Gap().getKey(),  new Image(IMAGE_PATH + "gap.png"));
		images.put(new Jelly().getKey(),  new Image(IMAGE_PATH + "jelly.png"));
		for (CandyColor cc: CandyColor.values()) {
			images.put(new Candy(cc).getFullKey(),   new Image(IMAGE_PATH + cc.toString().toLowerCase() + "Candy.png"));
		}
		for (CandyColor cc: CandyColor.values()) {
			wc.setColor(cc);
			images.put(wc.getFullKey(),  new Image(IMAGE_PATH + cc.toString().toLowerCase() + "Wrapped.png"));
		}
		for (CandyColor cc: CandyColor.values()) {
			vc.setColor(cc);
			images.put(vc.getFullKey(),  new Image(IMAGE_PATH + cc.toString().toLowerCase() + "VStripped.png"));
		}
		for (CandyColor cc: CandyColor.values()) {
			hc.setColor(cc);
			images.put(hc.getFullKey(),  new Image(IMAGE_PATH + cc.toString().toLowerCase() + "HStripped.png"));
		}
	}

	public Image getImage(Element e) {
		return images.get(e.getFullKey());
	}

	public Group getImage(Cell c) {
		Element extra = c.getExtra();
		ImageView bottom = new ImageView(images.get(c.getContent().getFullKey()));
		if (extra != null) { // TODO hasExtra
			ImageView top = new ImageView(images.get(c.getExtra().getFullKey()));
			top.setBlendMode(BlendMode.ADD);
			return new Group(bottom, top);
		}
		return new Group(bottom);
	}

}
