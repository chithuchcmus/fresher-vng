select * from message;
select * from conversation;
select * from participants;
select  * from user;

DROP TABLE message;
DROP TABLE participants;
DROP TABLE conversation;
drop table user;
drop table friends;

drop table friend_request;
drop table user;

drop table participants;

insert into participants (id_user, id_conversation) values (1,'479f812d-3f46-47b0-9808-64bbbaa11e51'),
                                                            (3,'479f812d-3f46-47b0-9808-64bbbaa11e51');

insert into participants (id_user, id_conversation) values (1,'asfnjdfj-sfndjbfsdbf-fndjfd'),
                                                            (2,'asfnjdfj-sfndjbfsdbf-fndjfd');

select * from participants p join conversation c on c.id_conversation = p.id_conversation
    where exists( select  * from  participants pp where
                 pp.id_user != 5 and pp.id_conversation =p.id_conversation)


select pp.id_conversation,pp.id_user,u.username from participants pp join user u on pp.id_user = u.id_user where
                    pp.id_conversation in (select id_conversation
                    from participants where id_user = 5) and pp.id_user != 5;

update user set  isOnline = 'ahii' where id_user=1;

select f.id_sender as id_user,u.username,u.email from friends f join user u on f.id_sender = u.id_user where f.id_receive = 1
union
select f.id_receive as id_user,uu.username,uu.email from friends f  join user uu on f.id_receive = uu.id_user where f.id_sender=1;
