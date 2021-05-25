package client;

public class RefreshContent implements Runnable{
    private ContentController contentController;
    private Client client;

    @Override
    public void run(){
        contentController.refreshCollection(client.askCollection());
    }
    public RefreshContent(ContentController contentController, Client client){
        this.contentController = contentController;
        this.client = client;
    }
}
