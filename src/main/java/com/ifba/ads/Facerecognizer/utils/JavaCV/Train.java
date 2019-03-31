package com.ifba.ads.Facerecognizer.utils.JavaCV;

import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_imgcodecs.IMREAD_GRAYSCALE;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.resize;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.IntBuffer;

import org.bytedeco.javacpp.opencv_face;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_face.FaceRecognizer;

import com.ifba.ads.Facerecognizer.utils.paths.Paths;

public class Train {
	
	public static final int IMG_SIZE = 160;
    
	public static boolean train(File[] files) throws Exception{
	    
	        MatVector photos = new MatVector(files.length);
	        Mat labels = new Mat(files.length, 1, CV_32SC1);
	        IntBuffer rotulosBuffer = labels.createBuffer();
	        int counter = 0;
	        for (File image : files) {
	            Mat photo = imread(image.getAbsolutePath(), IMREAD_GRAYSCALE);
	            int classe = Integer.parseInt(image.getName().split("\\.")[1]);
	            resize(photo, photo, new Size(IMG_SIZE, IMG_SIZE));
	            photos.put(counter, photo);
	            rotulosBuffer.put(counter, classe);
	            counter++;
	        }
	        
	        FaceRecognizer eigenfaces = opencv_face.EigenFaceRecognizer.create();
	        eigenfaces.train(photos, labels);
	        File f = new File(Paths.EIGEN_FACES_CLASSIFIER);
	        f.createNewFile();
	        eigenfaces.save(f.getAbsolutePath());
	        return true;
	    	
	    }

}
