package es.formulastudent.app.mvp.data.business;

public class DataLoader {

    private DataConsumer dataConsumer;

    protected void loadingData(boolean loading){
        if(dataConsumer != null){
            this.dataConsumer.loadingData(loading);
        }
    }

    public void setDataConsumer(DataConsumer dataConsumer) {
        this.dataConsumer = dataConsumer;
    }


    public interface Consumer{
        /**
         * Set data consumer
         * @param dataConsumer
         */
        void setDataConsumer(DataConsumer dataConsumer);
    }
}
