package pl.polsl.cotbd;

import com.ericbarnhill.niftijio.NiftiVolume;
import pl.polsl.cotbd.model.Image;


import java.io.IOException;

/**
 * @author krzys
 * @version 1.0
 * @created 03-gru-2021 21:30:39
 */
public class Files {
    private Files() {

    }

    public static Image readNifti(String path) throws IOException {
        NiftiVolume volume = NiftiVolume.read(path);
        float[] pixdim = volume.header.pixdim;
        return new Image(volume.data, pixdim[1] * pixdim[2] * pixdim[3]);
    }

}//end Files