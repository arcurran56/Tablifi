package tablifi.gui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;

class TabliformTest {

    MockTablifiForm mockForm;

    private class MockJButton extends JButton{
        public void click(){
            ActionEvent e = new ActionEvent(this,1,"click");
            this.fireActionPerformed(e);
        }
    }
    private class MockTablifiForm extends TablifiForm{


    }
    @BeforeEach
    void setUp() {
        MockTablifiForm mockForm = new MockTablifiForm();
    }

    @AfterEach
    void tearDown() {
        mockForm = null;
    }

    @Test
    void goButton(){

        MockJButton mockGoButton = (MockJButton) mockForm.goButton;
        mockGoButton.click();
    }

}