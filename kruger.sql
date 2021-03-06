PGDMP         3                z            kruger    13.3    13.3     ?           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            ?           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            ?           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            ?           1262    25878    kruger    DATABASE     b   CREATE DATABASE kruger WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Spanish_Spain.1252';
    DROP DATABASE kruger;
                postgres    false            ?            1259    25879    ft_usuarios    TABLE     [  CREATE TABLE public.ft_usuarios (
    pass character varying(100) DEFAULT NULL::character varying,
    nombre character varying(100) DEFAULT NULL::character varying,
    apellido character varying(100) DEFAULT NULL::character varying,
    correo character varying(100) DEFAULT NULL::character varying,
    fecha_nacimiento date,
    direccion character varying(100) DEFAULT NULL::character varying,
    telefono character varying(20) DEFAULT NULL::character varying,
    rol character varying(50) DEFAULT NULL::character varying,
    alta integer DEFAULT 0,
    cedula character varying(10) NOT NULL
);
    DROP TABLE public.ft_usuarios;
       public         heap    postgres    false            ?            1259    25895    ft_vacunacion    TABLE       CREATE TABLE public.ft_vacunacion (
    cedula character varying(10) NOT NULL,
    estado_vacunacion character varying(100) DEFAULT NULL::character varying,
    tipo_vacuna character varying,
    fecha_vacunacion date,
    numero_dosis integer DEFAULT 0
);
 !   DROP TABLE public.ft_vacunacion;
       public         heap    postgres    false            ?          0    25879    ft_usuarios 
   TABLE DATA              COPY public.ft_usuarios (pass, nombre, apellido, correo, fecha_nacimiento, direccion, telefono, rol, alta, cedula) FROM stdin;
    public          postgres    false    200   ?       ?          0    25895    ft_vacunacion 
   TABLE DATA           o   COPY public.ft_vacunacion (cedula, estado_vacunacion, tipo_vacuna, fecha_vacunacion, numero_dosis) FROM stdin;
    public          postgres    false    201          1           2606    25886    ft_usuarios ft_usuarios_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.ft_usuarios
    ADD CONSTRAINT ft_usuarios_pkey PRIMARY KEY (cedula);
 F   ALTER TABLE ONLY public.ft_usuarios DROP CONSTRAINT ft_usuarios_pkey;
       public            postgres    false    200            3           2606    25904     ft_vacunacion ft_vacunacion_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.ft_vacunacion
    ADD CONSTRAINT ft_vacunacion_pkey PRIMARY KEY (cedula);
 J   ALTER TABLE ONLY public.ft_vacunacion DROP CONSTRAINT ft_vacunacion_pkey;
       public            postgres    false    201            4           2606    25905    ft_vacunacion fk_cedula    FK CONSTRAINT        ALTER TABLE ONLY public.ft_vacunacion
    ADD CONSTRAINT fk_cedula FOREIGN KEY (cedula) REFERENCES public.ft_usuarios(cedula);
 A   ALTER TABLE ONLY public.ft_vacunacion DROP CONSTRAINT fk_cedula;
       public          postgres    false    201    2865    200            ?      x?????? ? ?      ?      x?????? ? ?     