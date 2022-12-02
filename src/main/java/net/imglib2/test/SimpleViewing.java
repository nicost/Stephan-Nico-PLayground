package net.imglib2.test;

import java.io.IOException;

import bdv.util.BdvFunctions;
import bdv.util.BdvOptions;
import bdv.util.BdvStackSource;
import ij.IJ;
import ij.ImageJ;
import net.imglib2.Cursor;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.img.imageplus.ImagePlusImgs;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.ARGBType;
import net.imglib2.type.numeric.NumericType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.util.Util;

public class SimpleViewing {

	/**
	 * Create an image from an existing float[] array and print out their
	 * locations and values.
	 * 
	 * @return
	 */
	public static Img<FloatType> createImgFromArray() {

		final float[] array = new float[5 * 5];

		for (int i = 0; i < array.length; ++i)
			array[i] = i;

		final Img<FloatType> img = ArrayImgs.floats(array, 5, 5);

		// print out all pixel values together with their position using a
		// Cursor
		final Cursor<FloatType> cursor = img.cursor();

		System.out.println("\nincreasing numbers: ");

		while (cursor.hasNext())
			System.out.println("value=" + cursor.next().get() + " @ " + Util.printCoordinates(cursor));

		return img;
	}

	/**
	 * View different images in ImageJ
	 */
	public static <T extends RealType<T> & NativeType<T>, S extends NumericType<S> & NativeType<S>> void viewingImageJ() {

		// show the ImageJ window
		new ImageJ();

		// display the image from Task1
		ImageJFunctions.show(createImgFromArray());

		// display blobs example (8 bit)
		final Img<T> img1 = ImagePlusImgs.from(IJ.openImage("http://imagej.nih.gov/ij/images/blobs.gif"));
		ImageJFunctions.show(img1);

		// display cloun example (RGB)
		final Img<S> img2 = ImagePlusImgs.from(IJ.openImage("http://imagej.nih.gov/ij/images/clown.jpg"));
		ImageJFunctions.show(img2);
	}

	/**
	 * View different images in BigDataViewer
	 */
	public static <T extends RealType<T> & NativeType<T>> void viewingBDV() {

		// display blobs example (8 bit) in new BDV instance
		final Img<UnsignedByteType> img3 = ImagePlusImgs.from(IJ.openImage("http://imagej.nih.gov/ij/images/blobs.gif"));
		BdvStackSource<?> bdv2 = BdvFunctions.show(img3, "2d UnsignedByteType image", new BdvOptions().is2D());

		// display cloun example (RGB)
		final Img<ARGBType> img4 = ImagePlusImgs.from(IJ.openImage("http://imagej.nih.gov/ij/images/clown.jpg"));
		bdv2 = BdvFunctions.show(img4, "2d ARGBType image", new BdvOptions().is2D().addTo(bdv2));
	}

	public static void main(String[] args) throws IOException {

		// various images displayed with ImageJ
		viewingImageJ();

		// various images displayed with BigDataViewer
		viewingBDV();
	}
}
