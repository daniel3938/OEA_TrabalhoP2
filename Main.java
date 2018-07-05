import java.io.IOException;
import java.io.RandomAccessFile;

public class Main 
{
 
    public static void main(String args[]) throws Exception 
    {
       if (args.length != 1) 
        {     
        	System.err.println("Erro na chamada do comando.");
            System.err.println("Uso: java Main [U || I || D]");
            System.err.println("U = União, I = Interseção, D = Diferença");
            System.out.println("Fim do programa.");
            System.exit(1);
        } 
    	RandomAccessFile f = new RandomAccessFile("cep1.dat", "r");
    	RandomAccessFile g = new RandomAccessFile("cep2.dat", "r");
    	RandomAccessFile h = new RandomAccessFile("resultado.dat","rw");
    	String tipo = args[0];
    	Endereco e = new Endereco();
    	    	
     	if (tipo.equals("U") || tipo.equals("I") || tipo.equals("D")) 
     	{
    		if (tipo.equals("U")) 
    		{
    			for(int i=0; i<f.length()/300;i++) 
    			{
    				f.seek(i*300);
    				e.leEndereco(f);
    				if (!buscaB(g, e.getCep())) 
    					e.escreveEndereco(h);
    				
    			}
    			for(int i=0; i<g.length()/300;i++) 
    			{
    				g.seek(i*300);
    				e.leEndereco(g);
    				e.escreveEndereco(h);
    			}
    		}
    		if (tipo.equals("I")) {
    			for(int i=0; i<f.length()/300;i++) 
    			{
    				f.seek(i*300);
    				e.leEndereco(f);
    				if (buscaB(g, e.getCep())) 
    					e.escreveEndereco(h);
    				
    			}
    			for(int i=0; i<g.length()/300;i++) 
    			{
    				g.seek(i*300);
    				e.leEndereco(g);
    				if (buscaB(f, e.getCep()) && !buscaB(h,e.getCep())) 
    					e.escreveEndereco(h);
    			}
    		}
    		if (tipo.equals("D")) {
    			for(int i=0; i<f.length()/300;i++) 
    			{
    				f.seek(i*300);
    				e.leEndereco(f);
    				if (!buscaB(g, e.getCep())) 
    					e.escreveEndereco(h);
    			}
       		}
    	}
    	else {
    		System.err.println("Erro na chamada do comando.");
            System.err.println("Uso: java Main [U || I || D]");
            System.err.println("U = União, I = Interseção, D = Diferença");
            System.out.println("Fim do programa.");
            System.exit(1);
    	}
    f.close();
    g.close();
    h.close();
    System.out.println("Fim do programa.");
    System.exit(1);
    }
    
    
    public static boolean buscaB (RandomAccessFile f, String ucep) throws IOException {
    	Endereco e = new Endereco();
        long tamanho = (f.length()/300);
        int cepa,cepb;
        String meioCEP;
            
        cepa = Integer.parseInt(ucep);
                
        f.seek(0);
        long primeiro = f.getFilePointer();
        
        f.seek((tamanho-1)*300);
        long ultimo = f.getFilePointer();
        
        f.seek((((ultimo/300)-(primeiro/300))/2)*300);
        long meio = f.getFilePointer();
        
        do 
        {
        	f.seek(meio);
        	e.leEndereco(f);
       		meioCEP = e.getCep();
       		
       		cepb = Integer.parseInt(meioCEP);
       		if (cepa == cepb) 
       		{
       			return true;
       		}
       		if (cepa < cepb)
       			ultimo = meio-300;
        	else if (cepa > cepb)
        		primeiro = meio+300;
        		
        	meio = (((primeiro+ultimo)/300)/2)*300;
        } while (primeiro <= ultimo);
        return false;
    }
}