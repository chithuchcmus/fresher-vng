#include <unistd.h> 
#include <stdio.h> 
#include <sys/socket.h> 
#include <stdlib.h> 
#include <netinet/in.h> 
#include <string.h>
#include <netinet/in.h>
#include <stdbool.h>
#define PORT 8080 //default connect with server at port 8080
#define SIZE_MESSAGE 1024
#define SIZE_BALL 1024
#define OUT_OF_BALL -1

int arrayBall[SIZE_BALL];
int currentIndexOfArray=0;

void writeNumberOnFile(int ball);
int getBallFromServer(int socket);
void sendFileToServer(int socket, char *filename);
void revcFileFromServer(int socket, char*filename);
void sortArrayOfBall();

char nameOfClient[100]="client";
char revBuffer[SIZE_MESSAGE];
char sendBufer[SIZE_MESSAGE];

int main(int argc, char *argv[])
{
    printf("%s\n",argv[1]);
    strcat(nameOfClient, argv[1]);

    int sock_server,n;
    struct sockaddr_in server_address;
    //create socket server
    sock_server = socket(AF_INET,SOCK_STREAM,0);

    if(sock_server <0)
    {
        perror("cann't create socket");
        exit(EXIT_FAILURE);
    }
    memset(&server_address, '\0', sizeof(server_address));
    server_address.sin_family = AF_INET; //match the socket() call 
    server_address.sin_port = htons( PORT ); // specify port to listen on

    //Dia chi ip/domain may chu
    if(inet_pton(AF_INET, "127.0.0.1", &server_address.sin_addr)<=0)
    {
        perror("inet_pton error occured");
        return 1;
    }
       //Goi ham connect de thuc hien mot ket noi den server
    if( connect(sock_server, (struct sockaddr *)&server_address, sizeof(server_address)) < 0)
    {
       printf("\n Error : Connect Failed \n");
       return 1;
    }
    int ballsize,ball;
    while(true)
    {
        send(sock_server,nameOfClient,strlen(nameOfClient),0);
        //gui va nhan du lieu bang byte 
        //Nhan du lieu tu server
        read(sock_server,&ballsize,sizeof(ballsize));
        ball = ntohl(ballsize);
        //server van con bong
        if(ball > 0 )
        {
            printf("message from server: %d\n",ball);  
            writeNumberOnFile(ball);
        }
        else
        {
            sendFileToServer(sock_server,nameOfClient);
            revcFileFromServer(sock_server,nameOfClient);
            break;
        }
    }
    if(shutdown(sock_server, SHUT_RDWR) != 0)
    {
        perror("Close socket fail\n");
    }
    return 0;
}

void writeNumberOnFile(int ball)
{   
    arrayBall[currentIndexOfArray++] = ball;
    sortArrayOfBall();
    FILE *fp = fopen(nameOfClient, "w+");
    for(int i=0;i < currentIndexOfArray -1 ;i++)
    {
        fprintf(fp, "%d\n", arrayBall[i]);
    }
    fprintf(fp, "%d", arrayBall[currentIndexOfArray-1]);
    fclose(fp);
}
int compare( const void* a, const void* b)
{
     int int_a = * ( (int*) a );
     int int_b = * ( (int*) b );

     if ( int_a == int_b ) return 0;
     else if ( int_a < int_b ) return -1;
     else return 1;
}

void sortArrayOfBall()
{
    qsort( arrayBall, currentIndexOfArray, sizeof(int), compare);
}
void sendFileToServer(int socket, char *filename)
{
    FILE* f = fopen(filename, "rb");
    // Calculate size of "file_name"
    fseek(f, 0, SEEK_END);
    int fsize = (int)ftell(f);
    fseek(f, 0, SEEK_SET);

    // Calculate number of loop require
    int numRepeated = 0;
    if (fsize % SIZE_MESSAGE)
        numRepeated = fsize / SIZE_MESSAGE + 1;
    else numRepeated = fsize / SIZE_MESSAGE;
    // Send numRepeated
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
void revcFileFromServer(int socket, char*filename)
{
    FILE* f = fopen(filename, "wb");
    
    // Receive numRepeated
    int numRepeated = 0;
    read(socket, &numRepeated, SIZE_MESSAGE);
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