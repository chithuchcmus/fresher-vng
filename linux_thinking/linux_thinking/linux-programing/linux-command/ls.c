#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include "dirent.h"
#include <time.h>
#include <pwd.h>
#include <grp.h>
#include <string.h>

#define KGRN  "\x1B[32m"
#define KBLU  "\x1B[34m"
#define RESET "\x1B[0m"
#define WHT   "\x1B[37m"


struct  InfoFile
{
    char permission[30];
    int n_link;
    char uid[30];
    char gid[30];
    int size;
    char time[20];
    char file_name[100];
    /* data */
};


char* getPermissionOfFile(mode_t filename)
{
    char *foo = malloc(sizeof(char) * 11);
     foo[0]= (char)((S_ISDIR(filename)) ? 'd' : '-');
     foo[1]= (char)((filename & S_IRUSR) ? 'r' : '-');
     foo[2]= (char)((filename & S_IWUSR) ? 'w' : '-');
     foo[3]= (char)((filename & S_IXUSR) ? 'x' : '-');
     foo[4]= (char)((filename & S_IRGRP) ? 'r' : '-');
     foo[5]=  (char)((filename& S_IWGRP) ? 'w' : '-');
     foo[6]= (char)((filename & S_IXGRP) ? 'x' : '-');
     foo[7]= (char)((filename& S_IROTH) ? 'r' : '-');
     foo[8]= (char)((filename& S_IWOTH) ? 'w' : '-');
     foo[9]= (char)((filename & S_IXOTH) ? 'x' : '-');
     foo[10]= '\0';
    return foo;
}
char* getTimeLastModify(time_t date)
{
    char* timeLastModify = ctime(&date);
    int size=13;
    char *result = malloc(sizeof(char) * size);
    for(int i=0;i< size -1;i++)
    {
        result[i]=timeLastModify[i+4];
    }
    result[size-1]='\0';
    return result;
}
char* getUID(uid_t uid)
{
    struct passwd *pw = getpwuid(uid);
    return pw->pw_name;
}
char* getGID(gid_t gid)
{
    struct group  *gr = getgrgid(gid);
    return gr->gr_name;
}
void printInfoFile(struct InfoFile *info, int size)
{   
    int i=0;
    while(i < size)
    {
        printf("%s",info[i].permission);
        //No. of Hard Links
        printf("%5d",info[i].n_link);
        //User Name
        printf(" %-8.8s",info[i].uid);
        //Group Name
        printf(" %-8.8s",info[i].gid);
        //File Size
        printf(" %8d",info[i].size);
        //Date and Time of modification
        printf(" %s\t",info[i].time);
        //file name
        if(info[i].n_link > 1)
        {
        printf(" %s%s%s\n",KGRN,info[i].file_name,RESET);
        }
        else 
        {
            printf(" %s%s%s\n",WHT,info[i].file_name,RESET);

        }
        i++;
    }
}
int main(int argc, char **argv)
{
    //default in directory don't have over 1000 file
    struct InfoFile infoFile[1000];

    DIR *mydir;
    struct dirent *myfile;
    struct stat mystat;
    char buf[512];    
    mydir = opendir(argv[1]);
    int sumsize=8; // file. and file .. in directory
    int current_file=0;
    while((myfile = readdir(mydir)) != NULL)
    {
        struct InfoFile temp;
        sprintf(buf, "%s/%s", argv[1], myfile->d_name);
        //ignore file .*
        if(myfile->d_name[0]=='.')
        {
            continue;
        }
        stat(buf, &mystat);
        //GET Permission
        strcpy(temp.permission,getPermissionOfFile(mystat.st_mode));
        //get number folder and file inside of file
        temp.n_link=(int)mystat.st_nlink;
        //get UID
        strcpy(temp.uid,getUID(mystat.st_uid));
        //GET GID
        strcpy(temp.gid,getGID(mystat.st_gid));
        //GET SIZE
        temp.size=(int)mystat.st_size;
        sumsize+= (int)(mystat.st_blocks);
        //convert time_t to char*
        strcpy(temp.time,getTimeLastModify(mystat.st_mtime));
        strcpy(temp.file_name,myfile->d_name);
        infoFile[current_file++]=temp;
    }
    //total: KB,number of 512B blocks allocated, amount of byte divide 1024 = KB
    printf("total: %d\n",sumsize*512/1024);
    printInfoFile(infoFile,current_file);
    closedir(mydir);
    return 0;
}