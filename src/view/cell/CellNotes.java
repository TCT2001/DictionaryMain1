package view.cell;

public class CellNotes extends Cell {
    public CellNotes(){
        button.setStyle("-fx-background-image: url('/view/image/noted.png');");
    }

    //dung interface goi may cai daubuoi
    @Override
    protected void actionHBoxClick() {
        super.actionHBoxClick();
    }

    @Override
    protected void actionButtonClick() {
        super.actionButtonClick();
    }
}
