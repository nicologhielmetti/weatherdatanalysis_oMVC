package Actions;

import State.Model.Datum;
import State.Model.Station;

import javax.servlet.http.Part;
import java.util.Vector;

public class UploadDataAction extends Action {
    private Integer idStation;
    private Part filePart;

    public UploadDataAction(Integer idStation, Part filePart) {
        super("@UPLOAD_DATA_ACTION", "@CONCRETE_RESOLVER");
        this.idStation = idStation;
        this.filePart = filePart;
    }

    public Integer getIdStation() {
        return idStation;
    }

    public Part getFilePart() {
        return filePart;
    }

    @Override
    public String toString() {
        return "UploadDataAction{" +
                "idStation=" + idStation +
                ", filePart=" + filePart +
                '}';
    }
}
