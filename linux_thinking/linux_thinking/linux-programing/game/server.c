#include <stdio.h> 
#include <stdlib.h> 
#include<sys/socket.h>
#include<netinet/in.h>
#include<string.h>
#include <arpa/inet.h>
#include <fcntl.h> // for open
#include <unistd.h> // for close
#include <sys/types.h>
#include <string.h>
#include <errno.h>
#include <netinet/in.h>
#include <pthread.h>
#include <time.h>


#define PORT 8080 //default connect with server at port 8080
#define SIZE_MESSAGE 1024
#define SIZE_NAME 100
#define MAX_CLIENT 10
#define OUT_OF_BALL -1

struct client
{
    int socket;
    int sum;
    pthread_t t;
    char nameOfClient[SIZE_NAME];
};


pthread_t ranking_thread;
struct client Clients[MAX_CLIENT];


int SIZE_BALL=0;
int sumClientConnected=0;
int currentClient=0;
int numberFileIsRecev=0;
int curentIndexOfArray=0;
char *resultFileName="result";


pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t countClient = PTHREAD_MUTEX_INITIALIZER;

void findAndGetSumBallClientWithSocket(int socket, int sum, char *clientName);
void *socketThread(void* clientSocket);
int* getRandomNumber();
void getFileFromClient(int socket, char* filename);
void sendResultToClient(int socket);
int getSumNumberOfFile(char *filename);
void recordResult();
void *rankingAndSendResultToClient(void *arg);
void rankClient();
char receivebuff[SIZE_MESSAGE];
int* dataNumber;


int main(int argc, char *argv[])
{
    //random size
    SIZE_BALL = rand() % 900 + 100;
    int socket_server,new_socket_client,port;
    struct sockaddr_in server_addr,client_addr;
    socklen_t addr_size;
    int block;
    int address=sizeof(address);
    int lengOfAddressClient;
    dataNumber = getRandomNumber(); 
    //Khoi tao gia tri cho cac vung nho
     memset(&server_addr, '\0', sizeof(server_addr));
     memset(&client_addr, '\0', sizeof(client_addr));
     //memset(sendbuff, '0', SIZE_MESSAGE);

    //create socket server
    socket_server = socket(AF_INET,SOCK_STREAM,0);
    if(socket_server==0)
    {
        perror("ERROR: cann't create socket\n");
        exit(EXIT_FAILURE);
    }
    server_addr.sin_family = AF_INET; //match the socket() call 
    server_addr.sin_addr.s_addr = inet_addr("127.0.0.1"); // bind to any local address 
    server_addr.sin_port = htons( PORT ); // specify port to listen on

    //bind
    if(bind(socket_server,(struct sockaddr *) &server_addr,sizeof(server_addr)) < 0)
    {
        perror("bind error");
        exit(EXIT_FAILURE); 
    }
    //listen, limit 5 node connect to server
    if(listen(socket_server,MAX_CLIENT) < 0)
    {
        perror("listen error"); 
        exit(EXIT_FAILURE); 
    }
    int *clientSocket;

    pthread_create(&ranking_thread,NULL,rankingAndSendResultToClient,NULL);
    while( (new_socket_client = accept(socket_server,(struct sockaddr*)&client_addr,  (socklen_t*)&addr_size)) >0)
    {   
        clientSocket=malloc(sizeof(int));
        *clientSocket=new_socket_client;
        pthread_mutex_lock(&countClient);
        Clients[currentClient].socket= *clientSocket;
        ++currentClient;
        pthread_mutex_unlock(&countClient);
        pthread_create(&Clients[currentClient].t,NULL,socketThread,(void*)clientSocket);
        
    }
    printf("hello\n");
    int sd;
    if((sd =shutdown(socket_server, SHUT_RDWR)) != 0)
    {
        perror("Close socket fail\n");
    }
    free(dataNumber);
    return 0;
}

void *socketThread(void* clientSocket)
{
    int newSocket = *((int *)clientSocket);
    //printf("socket hien tai %d\n",newSocket);
    char clientName[SIZE_NAME];
    while((recv(newSocket,receivebuff,SIZE_MESSAGE,0)>0))
    {
        int ball;
        if(curentIndexOfArray==SIZE_BALL)
        {
            printf("%s\n",clientName);
            ball=htonl(OUT_OF_BALL);
            //send message out of ball to client
            write(newSocket,&ball,sizeof(ball));
            //get file ball from client
            getFileFromClient(newSocket,clientName);
            //sum ball of client
            int sum=getSumNumberOfFile(clientName);
            findAndGetSumBallClientWithSocket(newSocket,sum,clientName);
            pthread_exit(NULL);
        }
        else
        {
            strcpy(clientName,receivebuff);
            pthread_mutex_lock(&mutex);
            ball = htonl(dataNumber[curentIndexOfArray++]);
            pthread_mutex_unlock(&mutex);
            //send ball to client
            send(newSocket,&ball,sizeof(ball),0);
        }
    }
}
void *rankingAndSendResultToClient(void *arg)
{
    while( curentIndexOfArray != SIZE_BALL)
    {
    }
    for(int i=0;i<currentClient;i++)
    {
        pthread_join(Clients[i].t,NULL);
    }
    rankClient();
    recordResult();
    for(int i=0;i < currentClient;i++)
    {
        sendResultToClient(Clients[i].socket);
    }
    for(int i=0;i< currentClient;i++)
    {
         shutdown(Clients[i].socket, SHUT_RDWR);
    }
    pthread_exit(NULL);
}

int* getRandomNumber()
{
    int *a = (int *) malloc( SIZE_BALL * sizeof( int ));
    srand(time(NULL));   
    printf("%d\n",SIZE_BALL);
    for(int i =0 ; i < SIZE_BALL;i++)
    {
        a[i]= rand() % 999 +1;
    }
    return a;
}
void getFileFromClient(int socket, char* filename)
{
    FILE* f = fopen(filename, "wb");
    // Receive number repeated
    int numRepeated = 0;
    read(socket, &numRepeated, sizeof(numRepeated));
    numRepeated = ntohl(numRepeated);

    // Receive file
    int n;
    char content[SIZE_MESSAGE] = {0};
    for (int i = 1; i <= numRepeated; i++)
    {   
        n = read(socket, content, SIZE_MESSAGE);
        fwrite(content, sizeof(char), n, f);
        memset(content, 0, SIZE_MESSAGE);
    }
    fclose(f);

}
void sendResultToClient(int socket)
{
    FILE* f = fopen(resultFileName, "rb");
    if (f == NULL)
    {
        perror("open to read faill \n");
    }
    //nhay den cuoi file
    fseek(f, 0, SEEK_END);
    //lay size of file
    int fileSize = (int)ftell(f);
    //nhay ve dau file
    fseek(f, 0, SEEK_SET);
    //tinh so lan lap roi gui den cho ben kia
    int numRepeated = 0;
    if (fileSize % SIZE_MESSAGE)
        numRepeated = fileSize / SIZE_MESSAGE + 1;
    else numRepeated = fileSize / SIZE_MESSAGE;
    // Send num_loop
    numRepeated = htonl(numRepeated);
    write(socket, &numRepeated, sizeof(numRepeated));
    numRepeated = ntohl(numRepeated);

    // Send file
    int n;
    char content[SIZE_MESSAGE] = {0};
    for (int i = 1; i <= numRepeated; i++)
    {
        n = fread(content, sizeof(char), SIZE_MESSAGE, f);
        write(socket, content, n);
        memset(content, 0, SIZE_MESSAGE);
    }
    fclose(f);
}
int getSumNumberOfFile(char *filename)
{    
    FILE *f = fopen(filename, "r");
    int iter = 0;
    int sum=0;
    if (f == NULL)
    {
        perror("open to read faill \n");
    }
    while((fscanf(f, "%d", &iter)) > 0)
    {
        sum+=iter;
    }
    fclose(f);
    return sum;

}
void findAndGetSumBallClientWithSocket(int socket, int sum, char *clientName)
{
    int i;
    for( i=0;i<currentClient;i++)
    {
        if(Clients[i].socket==socket)
        {
            Clients[i].sum=sum;
            strcpy(Clients[i].nameOfClient,clientName);
            break;
        }
    }
}

void rankClient()
{
    for(int i=0;i<currentClient-1;i++)
    {
        for(int j=i+1;j<currentClient;j++)
        {
            if(Clients[i].sum < Clients[j].sum)
            {
                struct client temp =Clients[j];
                Clients[j]=Clients[i];
                Clients[i] = temp;
            }
        }
    }

}

void recordResult()
{
    char result[SIZE_MESSAGE];
    FILE *fp = fopen(resultFileName, "w+");
    for(int i=0;i<currentClient;i++)
    {
        sprintf(result, "%s have sum %d. Top: %d\n", Clients[i].nameOfClient,Clients[i].sum,i +1 );
        //strcat(sendbuff,result);
        printf("%s\n",result);
        fputs(result, fp);
    }
    fclose(fp);
}