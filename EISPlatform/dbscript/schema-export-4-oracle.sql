drop table T_FF_DF_WORKFLOWDEF cascade constraints;
drop table T_FF_RT_JOINPOINT cascade constraints;
drop table T_FF_RT_PROCESSINSTANCE cascade constraints;
drop table T_FF_RT_PROCINST_VAR cascade constraints;
drop table T_FF_RT_TASKINSTANCE cascade constraints;
drop table T_FF_RT_TOKEN cascade constraints;
drop table T_FF_RT_WORKITEM cascade constraints;
create table T_FF_DF_WORKFLOWDEF (ID varchar2(50 char) not null, PROCESS_ID varchar2(100 char) not null, NAME varchar2(100 char) not null, DISPLAY_NAME varchar2(128 char), DESCRIPTION varchar2(1024 char), VERSION number(10,0) not null, PROCESS_CONTENT clob, PUBLISHED number(1,0), PUBLISHER varchar2(50 char), PUBLISH_TIME timestamp, primary key (ID));
create table T_FF_RT_JOINPOINT (ID varchar2(50 char) not null, SYNCHRONIZER_ID varchar2(200 char) not null, VALUE number(10,0), ALIVE number(1,0), PROCESSINSTANCE_ID varchar2(50 char) not null, primary key (ID));
create table T_FF_RT_PROCESSINSTANCE (ID varchar2(50 char) not null, PROCESS_ID varchar2(100 char) not null, VERSION number(10,0) not null, NAME varchar2(100 char), DISPLAY_NAME varchar2(128 char), STATE number(10,0), CREATED_TIME timestamp, STARTED_TIME timestamp, EXPIRED_TIME timestamp, END_TIME timestamp, PARENT_PROCESSINSTANCE_ID varchar2(50 char), PARENT_TASKINSTANCE_ID varchar2(50 char), primary key (ID));
create table T_FF_RT_PROCINST_VAR (PROCESSINSTANCE_ID varchar2(50 char) not null, VALUE varchar2(255 char), NAME varchar2(255 char) not null, primary key (PROCESSINSTANCE_ID, NAME));
create table T_FF_RT_TASKINSTANCE (ID varchar2(50 char) not null, BIZ_TYPE varchar2(250 char) not null, TASK_ID varchar2(300 char) not null, ACTIVITY_ID varchar2(200 char) not null, NAME varchar2(100 char) not null, DISPLAY_NAME varchar2(128 char), STATE number(10,0), TASK_TYPE varchar2(10 char), CREATED_TIME timestamp not null, STARTED_TIME timestamp, EXPIRED_TIME timestamp, END_TIME timestamp, ASSIGNMENT_STRATEGY varchar2(10 char) not null, PROCESSINSTANCE_ID varchar2(50 char) not null, PROCESS_ID varchar2(100 char) not null, VERSION number(10,0) not null, primary key (ID));
create table T_FF_RT_TOKEN (ID varchar2(50 char) not null, ALIVE number(1,0) not null, VALUE number(10,0) not null, NODE_ID varchar2(200 char) not null, PROCESSINSTANCE_ID varchar2(50 char) not null, primary key (ID));
create table T_FF_RT_WORKITEM (ID varchar2(50 char) not null, STATE number(10,0) not null, CREATED_TIME timestamp not null, SIGNED_TIME timestamp, END_TIME timestamp, ACTOR_ID varchar2(50 char), COMMENTS varchar2(1024 char), TASKINSTANCE_ID varchar2(50 char) not null, primary key (ID));
alter table T_FF_RT_PROCINST_VAR add constraint FKD79C420D7AF471D8 foreign key (PROCESSINSTANCE_ID) references T_FF_RT_PROCESSINSTANCE;
alter table T_FF_RT_WORKITEM add constraint FK4131554DE2527DDC foreign key (TASKINSTANCE_ID) references T_FF_RT_TASKINSTANCE;

-- Add comments to the T_FF_RT_PROCESSINSTANCE columns 
comment on column T_FF_RT_PROCESSINSTANCE.ID
  is '����ʵ��Id';
comment on column T_FF_RT_PROCESSINSTANCE.PROCESS_ID
  is '����Id';
comment on column T_FF_RT_PROCESSINSTANCE.VERSION
  is '���̰汾';
comment on column T_FF_RT_PROCESSINSTANCE.NAME
  is '��������';
comment on column T_FF_RT_PROCESSINSTANCE.DISPLAY_NAME
  is '������ʾ��';
comment on column T_FF_RT_PROCESSINSTANCE.STATE
  is '����ʵ��״̬';
comment on column T_FF_RT_PROCESSINSTANCE.CREATED_TIME
  is '����ʵ������ʱ��';
comment on column T_FF_RT_PROCESSINSTANCE.STARTED_TIME
  is '����ʵ������ʱ��';
comment on column T_FF_RT_PROCESSINSTANCE.EXPIRED_TIME
  is '����ʵ���������';
comment on column T_FF_RT_PROCESSINSTANCE.END_TIME
  is '����ʵ������ʱ��';
comment on column T_FF_RT_PROCESSINSTANCE.PARENT_PROCESSINSTANCE_ID
  is '������ʵ��Id';
comment on column T_FF_RT_PROCESSINSTANCE.PARENT_TASKINSTANCE_ID
  is '��Ӧ�ĸ�����ʵ��Id';
  
  
-- Add comments to the T_FF_RT_TASKINSTANCE columns 
comment on column T_FF_RT_TASKINSTANCE.ID
  is '����ʵ��Id';
comment on column T_FF_RT_TASKINSTANCE.BIZ_TYPE
  is 'ҵ���������֧��hibernate��orm���߽��и��Ӷ���ӳ��';
comment on column T_FF_RT_TASKINSTANCE.TASK_ID
  is '����Id';
comment on column T_FF_RT_TASKINSTANCE.ACTIVITY_ID
  is '����Id';
comment on column T_FF_RT_TASKINSTANCE.NAME
  is '����';
comment on column T_FF_RT_TASKINSTANCE.DISPLAY_NAME
  is '��ʾ��';
comment on column T_FF_RT_TASKINSTANCE.STATE
  is '����ʵ��״̬';
comment on column T_FF_RT_TASKINSTANCE.TASK_TYPE
  is '�������ͣ�ȡֵ FORM,TOOL,SUBFLOW';
comment on column T_FF_RT_TASKINSTANCE.CREATED_TIME
  is '����ʵ������ʱ��';
comment on column T_FF_RT_TASKINSTANCE.STARTED_TIME
  is '����ʵ������ʱ��';
comment on column T_FF_RT_TASKINSTANCE.EXPIRED_TIME
  is '����ʵ���������';
comment on column T_FF_RT_TASKINSTANCE.END_TIME
  is '����ʵ������ʱ��';
comment on column T_FF_RT_TASKINSTANCE.ASSIGNMENT_STRATEGY
  is '���������ԣ�ȡֵ ALL,ANY';
comment on column T_FF_RT_TASKINSTANCE.PROCESSINSTANCE_ID
  is '����ʵ��Id����T_FF_RT_ProcessInstance�������';
comment on column T_FF_RT_TASKINSTANCE.PROCESS_ID
  is '����Id�������ֶΣ�';
comment on column T_FF_RT_TASKINSTANCE.VERSION
  is '���̰汾�������ֶΣ�';  
  
-- Add comments to the T_FF_RT_WORKITEM columns 
comment on column T_FF_RT_WORKITEM.ID
  is '����Id';
comment on column T_FF_RT_WORKITEM.STATE
  is '����״̬';
comment on column T_FF_RT_WORKITEM.CREATED_TIME
  is '��������ʱ��';
comment on column T_FF_RT_WORKITEM.SIGNED_TIME
  is '����ǩ��ʱ��';
comment on column T_FF_RT_WORKITEM.END_TIME
  is '��������ʱ��';
comment on column T_FF_RT_WORKITEM.ACTOR_ID
  is '����ԱId';
comment on column T_FF_RT_WORKITEM.COMMENTS
  is '��ע';
comment on column T_FF_RT_WORKITEM.TASKINSTANCE_ID
  is '����ʵ��Id����T_FF_RT_TaskInstance�������';
  
  
-- Add comments to the T_FF_RT_PROCINST_VAR columns 
comment on column T_FF_RT_PROCINST_VAR.PROCESSINSTANCE_ID
  is '����ʵ��ID����T_FF_RT_ProcessInstance�������';
comment on column T_FF_RT_PROCINST_VAR.VALUE
  is 'ʵ������ֵ';
comment on column T_FF_RT_PROCINST_VAR.NAME
  is 'ʵ����������';
  
  
-- Add comments to the T_FF_DF_WORKFLOWDEF columns 
comment on column T_FF_DF_WORKFLOWDEF.ID
  is '����¼��Id';
comment on column T_FF_DF_WORKFLOWDEF.PROCESS_ID
  is '����Id';
comment on column T_FF_DF_WORKFLOWDEF.NAME
  is '��������';
comment on column T_FF_DF_WORKFLOWDEF.DISPLAY_NAME
  is '������ʾ��';
comment on column T_FF_DF_WORKFLOWDEF.DESCRIPTION
  is '��������';
comment on column T_FF_DF_WORKFLOWDEF.VERSION
  is '���̰汾';
comment on column T_FF_DF_WORKFLOWDEF.PROCESS_CONTENT
  is '���̶����ļ�';
comment on column T_FF_DF_WORKFLOWDEF.PUBLISHED
  is '����״̬';
comment on column T_FF_DF_WORKFLOWDEF.PUBLISHER
  is '������';
comment on column T_FF_DF_WORKFLOWDEF.PUBLISH_TIME
  is '����ʱ��';
  

