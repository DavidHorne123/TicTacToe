package edu.sdccd.cisc191.template;
import edu.sdccd.cisc191.template.ServerRequest;
import javafx.geometry.Insets;
import javafx.scene.control.Label;

/**
 * Extends the basic JavaFX Label with game functionality
 */
public class GameBoardLabel<T> extends Label {

    public static Insets LABEL_PADDING = new Insets(20);

    // Generic data associated with this label
    private T associatedData;

    public GameBoardLabel() {
        // set Label properties like padding
        // adds space between the text
        setPadding(LABEL_PADDING);
    }
    /**
     * Gets the data associated with this label
     * @return the associated data
     */
    public T getAssociatedData() {
        return associatedData;
    }
    /**
     * Sets the data associated with this label
     * @param associatedData the data to associate with this label
     */
    public void setAssociatedData(T associatedData) {
        this.associatedData = associatedData;
    }

    /**
     * Updates the label's text to match the associated data (if not null)
     */
    public void updateLabelText() {
        if (associatedData != null) {
            setText(associatedData.toString());
        }
    }


}
