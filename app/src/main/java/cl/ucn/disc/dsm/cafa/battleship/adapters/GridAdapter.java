package cl.ucn.disc.dsm.cafa.battleship.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import cl.ucn.disc.dsm.cafa.battleship.R;
import cl.ucn.disc.dsm.cafa.battleship.model.CellStatus;
import lombok.Getter;

import static cl.ucn.disc.dsm.cafa.battleship.MainActivity.DIMENSION;

public class GridAdapter extends BaseAdapter {
    private LayoutInflater inflater;

    private List<GridCell> cells;

    /**
     * Constructor del adaptador.
     * @param context
     * @param cells
     */
    public GridAdapter(Context context, List<GridCell> cells){
        this.inflater = LayoutInflater.from(context);
        this.cells = cells;
    }

    public void setNewEmptyGrid(){
        this.cells = new ArrayList<>();
        for (int i = 0; i< DIMENSION; i++){
            for (int j = 0; j< DIMENSION; j++){
                cells.add(new GridCell(j,i));
            }
        }
    }

    @Override
    public int getCount() {
        if (this.cells != null)
            return this.cells.size();
        return 0;
    }

    @Override
    public GridCell getItem(int position) {
        if (this.cells != null)
            return this.cells.get(position);
        return null;
    }

    public GridCell getItemByCoordinates(int xCoord, int yCoord){
        if (this.cells != null)
            for (GridCell cell: this.cells)
                if (cell.getXCoord() == xCoord && cell.getYCoord() == yCoord)
                    return cell;

        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Obtener el tamanio de las columnas.
        GridView grid = (GridView)parent;
        int size = grid.getColumnWidth();

        // Patron viewholder.
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.gridcell, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final GridCell cell = getItem(position);

        // TODO: En desarrollo.
        if (cell.getStatus() == CellStatus.USED_BY_PLAYER_1){
            holder.getButton().setBackgroundColor(cell.getColorWithShip());

        } else if (cell.getStatus() == CellStatus.USED_BY_PLAYER_2){
            // Para no mostrar las celdas del enemigo:
            holder.getButton().setBackgroundColor(CellStatus.EMPTY.getColor());

        } else {
            // Casos HIT, MISS, EMPTY
            holder.getButton().setBackgroundColor(cell.getStatus().getColor());
        }

        // Permite que las celdas sean cuadradas.
        convertView.setLayoutParams(new GridView.LayoutParams(size,size));

        return convertView;
    }


    class ViewHolder{
        @Getter
        final Button button;

        public ViewHolder(final View convertView){
            this.button = convertView.findViewById(R.id.cell_button);
        }
    }

}
