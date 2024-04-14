package tablifi.gui;

import tablifi.ExtractAnnotationsHelper;

import javax.swing.*;
import java.io.File;
import java.util.concurrent.ExecutionException;

public class GoWorker extends SwingWorker<String, Object> {
    GoCallback caller;

    GoWorker(GoCallback caller ) {
        this.caller = caller;
    }
    @Override
    protected String doInBackground() {
        return ExtractAnnotationsHelper.extract(caller.getFile());
    }
    @Override
    protected void done() {
        String result;
        try {
            result = get();
        } catch (InterruptedException e) {
            result = e.toString();
        } catch (ExecutionException e) {
            result = e.toString();
        }
        caller.goDone(result);

    }
}
