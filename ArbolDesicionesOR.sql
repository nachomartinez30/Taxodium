declare
	@Familia_APG nvarchar(255)
	,@Genero_APG nvarchar(255)
	,@Nombre_APG nvarchar(255)
	,@Especie_APG nvarchar(255)
	,@Clave_UMAFOR int
	,@IdEstado int
	,@Eco_N4	nvarchar(255)
	,@Eco_N3	nvarchar(255)
	,@Eco_N2	float
	,@Eco_N1	int
	,@prioridad int
	
	,@Fuente1 varchar(255)
	,@Fuente2 varchar(255)
	,@Fuente3 varchar(255)
	,@Fuente4 varchar(255)

SET @Fuente1 = 'Siplafor'
SET @Fuente2 = '1erINF61-85'

/*SET @Familia_APG = ''
SET @Genero_APG = ''
SET @Especie_APG = ''
SET @Nombre_APG = ''
SET @Clave_UMAFOR= 
SET @IdEstado = 
SET @Eco_N4 = '...'
SET @Eco_N3 = '..'
SET @Eco_N2 = .
SET @Eco_N1 = */



SELECT 
[Clave_ecuacion],
Num_Observaciones,
R2,
--
(CASE 
 				--NIVEL_1
WHEN  [Nombre_APG] =@Nombre_APG and [Clave_UMAFOR]=@Clave_UMAFOR  and [Fuente]='Siplafor' and @Especie_APG is not NULL	THEN 	1
WHEN [Nombre_APG] =@Nombre_APG and [idEstado]=@IdEstado and [Fuente]='Siplafor' and @Especie_APG is not NULL 	THEN  2
WHEN [Nombre_APG] =@Nombre_APG and [idEstado]=@IdEstado and [Eco_N4]=@Eco_N4 and [Fuente]='1erINF61-85' and @Especie_APG is not NULL 	THEN  3
WHEN [Nombre_APG] =@Nombre_APG and [idEstado]=@IdEstado and [Fuente]='1erINF61-85' and @Especie_APG is not NULL  	THEN  4
				--NIVEL_2
WHEN [Nombre_APG] =@Nombre_APG and [Eco_N4]=@Eco_N4 and [Fuente]='Siplafor' 	and @Especie_APG is not NULL THEN  5
WHEN [Nombre_APG] =@Nombre_APG and [Eco_N3]=@Eco_N3 and [Fuente]='Siplafor' 	and @Especie_APG is not NULL THEN  6
WHEN [Nombre_APG] =@Nombre_APG and [Eco_N2]=@Eco_N2 and [Fuente]='Siplafor' 	and @Especie_APG is not NULL THEN  7
WHEN [Nombre_APG] =@Nombre_APG and [Eco_N1]=@Eco_N1 and [Fuente]='Siplafor' 	and @Especie_APG is not NULL THEN  8
WHEN [Nombre_APG] =@Nombre_APG and [Eco_N4]=@Eco_N4 and [Fuente]='1erINF61-85' 	and @Especie_APG is not NULL THEN  9
WHEN [Nombre_APG] =@Nombre_APG and [Eco_N3]=@Eco_N3 and [Fuente]='1erINF61-85' 	and @Especie_APG is not NULL THEN  10
WHEN [Nombre_APG] =@Nombre_APG and [Eco_N2]=@Eco_N2 and [Fuente]='1erINF61-85' 	and @Especie_APG is not NULL THEN  11
WHEN [Nombre_APG] =@Nombre_APG and [Eco_N1]=@Eco_N1 and [Fuente]='1erINF61-85' 	and @Especie_APG is not NULL THEN  12

--GENERO
				--NIVEL_3
WHEN [Nombre_APG] =@Genero_APG and [Clave_UMAFOR]=@Clave_UMAFOR  and [Fuente]='Siplafor' 	THEN  13
WHEN [Nombre_APG] =@Genero_APG and [idEstado]=@IdEstado and [Fuente]='Siplafor' 	THEN  14

WHEN [Nombre_APG] =@Genero_APG and [idEstado]=@IdEstado and [Eco_N4]=@Eco_N4 and [Fuente]='1erINF61-85' 	THEN  15
WHEN [Nombre_APG] =@Genero_APG and [idEstado]=@IdEstado and [Fuente]='1erINF61-85'  	THEN  16
				--NIVEL_4
WHEN [Nombre_APG] =@Genero_APG and [Eco_N4]=@Eco_N4 and [Fuente]='Siplafor' 	THEN  17
WHEN [Nombre_APG] =@Genero_APG and [Eco_N3]=@Eco_N3 and [Fuente]='Siplafor' 	THEN  18
WHEN [Nombre_APG] =@Genero_APG and [Eco_N2]=@Eco_N2 and [Fuente]='Siplafor' 	THEN  19
WHEN [Nombre_APG] =@Genero_APG and [Eco_N1]=@Eco_N1 and [Fuente]='Siplafor' 	THEN  20
WHEN [Nombre_APG] =@Genero_APG and [Eco_N4]=@Eco_N4 and [Fuente]='1erINF61-85' 	THEN  21
WHEN [Nombre_APG] =@Genero_APG and [Eco_N3]=@Eco_N3 and [Fuente]='1erINF61-85' 	THEN  22
WHEN [Nombre_APG] =@Genero_APG and [Eco_N2]=@Eco_N2 and [Fuente]='1erINF61-85' 	THEN  23
WHEN [Nombre_APG] =@Genero_APG and [Eco_N1]=@Eco_N1 and [Fuente]='1erINF61-85' 	THEN  24
--FAMILIA
				--NIVEL_5
WHEN [Familia_APG] =@Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL and [Clave_UMAFOR]=@Clave_UMAFOR  and [Fuente]='Siplafor' 	THEN  25
WHEN [Familia_APG] =@Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL and [idEstado]=@IdEstado and [Fuente]='Siplafor' 	THEN  26
WHEN [Familia_APG] =@Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL and [idEstado]=@IdEstado and [Eco_N4]=@Eco_N4 and [Fuente]='1erINF61-85' 	THEN  27
WHEN [Familia_APG] =@Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL and [idEstado]=@IdEstado and [Fuente]='1erINF61-85'  	THEN  28
				--NIVEL_6
WHEN [Familia_APG] = @Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL  and [Eco_N4]=@Eco_N4 and [Fuente]='Siplafor' 	THEN  29
WHEN [Familia_APG] = @Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL  and [Eco_N3]=@Eco_N3 and [Fuente]='Siplafor' 	THEN  30
WHEN [Familia_APG] = @Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL  and [Eco_N2]=@Eco_N2 and [Fuente]='Siplafor' 	THEN  31
WHEN [Familia_APG] = @Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL  and [Eco_N1]=@Eco_N1 and [Fuente]='Siplafor' 	THEN  32
WHEN [Familia_APG] = @Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL  and [Eco_N4]=@Eco_N4 and [Fuente]='1erINF61-85' 	THeN 33
WHEN [Familia_APG] = @Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL  and [Eco_N3]=@Eco_N3 and [Fuente]='1erINF61-85' 	THEN  34
WHEN [Familia_APG] = @Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL  and [Eco_N2]=@Eco_N2 and [Fuente]='1erINF61-85' 	THEN  35
WHEN [Familia_APG] = @Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL  and [Eco_N1]=@Eco_N1 and [Fuente]='1erINF61-85' 	THEN  36
--Afinidad GENERO
				--NIVEL_7
WHEN [Genero_APG] =@Genero_APG and [Clave_UMAFOR]=@Clave_UMAFOR  and [Fuente]='Siplafor' 	THEN  37
WHEN [Genero_APG] =@Genero_APG and [idEstado]=@IdEstado and [Fuente]='Siplafor' 	THEN  38
WHEN [Genero_APG] =@Genero_APG and [idEstado]=@IdEstado and [Fuente]='1erINF61-85'  	THEN  39
				--NIVEL_8
WHEN [Genero_APG] =@Genero_APG and [Eco_N4]=@Eco_N4 and [Fuente]='Siplafor' 	THEN  40
WHEN [Genero_APG] =@Genero_APG and [Eco_N3]=@Eco_N3 and [Fuente]='Siplafor' 	THEN  41
WHEN [Genero_APG] =@Genero_APG and [Eco_N2]=@Eco_N2 and [Fuente]='Siplafor' 	THEN  42
WHEN [Genero_APG] =@Genero_APG and [Eco_N1]=@Eco_N1 and [Fuente]='Siplafor' 	THEN  43
WHEN [Genero_APG] =@Genero_APG and [Eco_N4]=@Eco_N4 and [Fuente]='1erINF61-85' 	THEN  44
WHEN [Genero_APG] =@Genero_APG and [Eco_N3]=@Eco_N3 and [Fuente]='1erINF61-85' 	THEN  45
WHEN [Genero_APG] =@Genero_APG and [Eco_N2]=@Eco_N2 and [Fuente]='1erINF61-85' 	THEN  46
WHEN [Genero_APG] =@Genero_APG and [Eco_N1]=@Eco_N1 and [Fuente]='1erINF61-85' 	THEN  47
--RESTO ESPECIES
				--NIVEL_9
WHEN [Clave_UMAFOR]=@Clave_UMAFOR AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL  and [Fuente]='Siplafor' 	THEN  48
WHEN [idEstado]=@IdEstado AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='Siplafor' 	THEN  49
WHEN [idEstado]=@IdEstado AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='1erINF61-85'  	THEN  50
				--NIVEL_10
WHEN [Eco_N4]=@Eco_N4 AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='Siplafor' 	THEN  51
WHEN [Eco_N3]=@Eco_N3 AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='Siplafor' 	THEN  52
WHEN [Eco_N2]=@Eco_N2 AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='Siplafor' 	THEN  53
WHEN [Eco_N1]=@Eco_N1 AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='Siplafor' 	THEN  54
WHEN [Eco_N4]=@Eco_N4 AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='1erINF61-85' 	THeN 55
WHEN [Eco_N3]=@Eco_N3 AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='1erINF61-85' 	THEN  56
WHEN [Eco_N2]=@Eco_N2 AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='1erINF61-85' 	THEN  57
WHEN [Eco_N1]=@Eco_N1 AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='1erINF61-85' 	THEN  58
/**/
 end) as 'Prioridad_Arbol'
--
FROM 
[ArbolDesiciones].[dbo].[Ecuaciones_VRTA_2]
WHERE 
--ESPECIES
				--NIVEL_1
   [Nombre_APG] =@Nombre_APG and [Clave_UMAFOR]=@Clave_UMAFOR  and [Fuente]='Siplafor'		 and  @Especie_APG is not NULL--PRIORIDAD_1
or [Nombre_APG] =@Nombre_APG and [idEstado]=@IdEstado and [Fuente]='Siplafor' 	 and  @Especie_APG is not NULL--PRIORIDAD_2
or [Nombre_APG] =@Nombre_APG and [idEstado]=@IdEstado and [Eco_N4]=@Eco_N4 and [Fuente]='1erINF61-85' 	 and  @Especie_APG is not NULL--PRIORIDAD_3
or [Nombre_APG] =@Nombre_APG and [idEstado]=@IdEstado and [Fuente]='1erINF61-85'  	 and  @Especie_APG is not NULL--PRIORIDAD_4
				--NIVEL_2
or [Nombre_APG] =@Nombre_APG and [Eco_N4]=@Eco_N4 and [Fuente]='Siplafor' 	and  @Especie_APG is not NULL --PRIORIDAD_5
or [Nombre_APG] =@Nombre_APG and [Eco_N3]=@Eco_N3 and [Fuente]='Siplafor' 	and  @Especie_APG is not NULL --PRIORIDAD_6
or [Nombre_APG] =@Nombre_APG and [Eco_N2]=@Eco_N2 and [Fuente]='Siplafor' 	and  @Especie_APG is not NULL --PRIORIDAD_7
or [Nombre_APG] =@Nombre_APG and [Eco_N1]=@Eco_N1 and [Fuente]='Siplafor' 	and  @Especie_APG is not NULL --PRIORIDAD_8
or [Nombre_APG] =@Nombre_APG and [Eco_N4]=@Eco_N4 and [Fuente]='1erINF61-85' 	and  @Especie_APG is not NULL --PRIORIDAD_9
or [Nombre_APG] =@Nombre_APG and [Eco_N3]=@Eco_N3 and [Fuente]='1erINF61-85' 	and  @Especie_APG is not NULL --PRIORIDAD_10
or [Nombre_APG] =@Nombre_APG and [Eco_N2]=@Eco_N2 and [Fuente]='1erINF61-85' 	and  @Especie_APG is not NULL --PRIORIDAD_11
or [Nombre_APG] =@Nombre_APG and [Eco_N1]=@Eco_N1 and [Fuente]='1erINF61-85' 	and  @Especie_APG is not NULL --PRIORIDAD_12

--GENERO
				--NIVEL_3
or [Nombre_APG] =@Genero_APG and [Clave_UMAFOR]=@Clave_UMAFOR  and [Fuente]='Siplafor' 	--PRIORIDAD_13
or [Nombre_APG] =@Genero_APG and [idEstado]=@IdEstado and [Fuente]='Siplafor' 	--PRIORIDAD_
or [Nombre_APG] =@Genero_APG and [idEstado]=@IdEstado and [Eco_N4]=@Eco_N4 and [Fuente]='1erINF61-85' 	--PRIORIDAD_14
or [Nombre_APG] =@Genero_APG and [idEstado]=@IdEstado and [Fuente]='1erINF61-85'  	--PRIORIDAD_15
				--NIVEL_4
or [Nombre_APG] =@Genero_APG and [Eco_N4]=@Eco_N4 and [Fuente]='Siplafor' 	--PRIORIDAD_16
or [Nombre_APG] =@Genero_APG and [Eco_N3]=@Eco_N3 and [Fuente]='Siplafor' 	--PRIORIDAD_17
or [Nombre_APG] =@Genero_APG and [Eco_N2]=@Eco_N2 and [Fuente]='Siplafor' 	--PRIORIDAD_18
or [Nombre_APG] =@Genero_APG and [Eco_N1]=@Eco_N1 and [Fuente]='Siplafor' 	--PRIORIDAD_19
or [Nombre_APG] =@Genero_APG and [Eco_N4]=@Eco_N4 and [Fuente]='1erINF61-85' 	--PRIORIDAD_20
or [Nombre_APG] =@Genero_APG and [Eco_N3]=@Eco_N3 and [Fuente]='1erINF61-85' 	--PRIORIDAD_21
or [Nombre_APG] =@Genero_APG and [Eco_N2]=@Eco_N2 and [Fuente]='1erINF61-85' 	--PRIORIDAD_22
or [Nombre_APG] =@Genero_APG and [Eco_N1]=@Eco_N1 and [Fuente]='1erINF61-85' 	--PRIORIDAD_23
--FAMILIA
				--NIVEL_5
or [Familia_APG]  =@Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL and [Clave_UMAFOR]=@Clave_UMAFOR  and [Fuente]='Siplafor' 	--PRIORIDAD_24
or [Familia_APG]  =@Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL and [idEstado]=@IdEstado and [Fuente]='Siplafor' 	--PRIORIDAD_25
or [Familia_APG]  =@Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL and [idEstado]=@IdEstado and [Eco_N4]=@Eco_N4 and [Fuente]='1erINF61-85' 	--PRIORIDAD_26
or [Familia_APG]  =@Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL and [idEstado]=@IdEstado and [Fuente]='1erINF61-85'  	--PRIORIDAD_27
				--NIVEL_6
or [Familia_APG]  =@Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL and [Eco_N4]=@Eco_N4 and [Fuente]='Siplafor' 	--PRIORIDAD_28
or [Familia_APG]  =@Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL and [Eco_N3]=@Eco_N3 and [Fuente]='Siplafor' 	--PRIORIDAD_29
or [Familia_APG]  =@Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL and [Eco_N2]=@Eco_N2 and [Fuente]='Siplafor' 	--PRIORIDAD_30
or [Familia_APG]  =@Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL and [Eco_N1]=@Eco_N1 and [Fuente]='Siplafor' 	--PRIORIDAD_31
or [Familia_APG]  =@Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL and [Eco_N4]=@Eco_N4 and [Fuente]='1erINF61-85' 	--PRIORIDAD_32
or [Familia_APG]  =@Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL and [Eco_N3]=@Eco_N3 and [Fuente]='1erINF61-85' 	--PRIORIDAD_33
or [Familia_APG]  =@Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL and [Eco_N2]=@Eco_N2 and [Fuente]='1erINF61-85' 	--PRIORIDAD_34
or [Familia_APG]  =@Familia_APG and @Genero_APG is NULL and @Especie_APG is NULL and [Eco_N1]=@Eco_N1 and [Fuente]='1erINF61-85' 	--PRIORIDAD_35
--Afinidad GENERO
				--NIVEL_7
or [Genero_APG] =@Genero_APG and [Clave_UMAFOR]=@Clave_UMAFOR  and [Fuente]='Siplafor' 	--PRIORIDAD_36
or [Genero_APG] =@Genero_APG and [idEstado]=@IdEstado and [Fuente]='Siplafor' 	--PRIORIDAD_37
or [Genero_APG] =@Genero_APG and [idEstado]=@IdEstado and [Fuente]='1erINF61-85'  	--PRIORIDAD_38
				--NIVEL_8
or [Genero_APG] =@Genero_APG and [Eco_N4]=@Eco_N4 and [Fuente]='Siplafor' 	--PRIORIDAD_39
or [Genero_APG] =@Genero_APG and [Eco_N3]=@Eco_N3 and [Fuente]='Siplafor' 	--PRIORIDAD_40
or [Genero_APG] =@Genero_APG and [Eco_N2]=@Eco_N2 and [Fuente]='Siplafor' 	--PRIORIDAD_41
or [Genero_APG] =@Genero_APG and [Eco_N1]=@Eco_N1 and [Fuente]='Siplafor' 	--PRIORIDAD_42
or [Genero_APG] =@Genero_APG and [Eco_N4]=@Eco_N4 and [Fuente]='1erINF61-85' 	--PRIORIDAD_43
or [Genero_APG] =@Genero_APG and [Eco_N3]=@Eco_N3 and [Fuente]='1erINF61-85' 	--PRIORIDAD_44
or [Genero_APG] =@Genero_APG and [Eco_N2]=@Eco_N2 and [Fuente]='1erINF61-85' 	--PRIORIDAD_45
or [Genero_APG] =@Genero_APG and [Eco_N1]=@Eco_N1 and [Fuente]='1erINF61-85' 	--PRIORIDAD_46
--RESTO ESPECIES
				--NIVEL_9
or [Clave_UMAFOR]=@Clave_UMAFOR  AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='Siplafor' 	--PRIORIDAD_47
or [idEstado]=@IdEstado AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='Siplafor' 	--PRIORIDAD_48
or [idEstado]=@IdEstado AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='1erINF61-85'  	--PRIORIDAD_49
				--NIVEL_10
or [Eco_N4]=@Eco_N4 AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='Siplafor' 	--PRIORIDAD_50
or [Eco_N3]=@Eco_N3 AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='Siplafor' 	--PRIORIDAD_51
or [Eco_N2]=@Eco_N2 AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='Siplafor' 	--PRIORIDAD_52
or [Eco_N1]=@Eco_N1 AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='Siplafor' 	--PRIORIDAD_53
or [Eco_N4]=@Eco_N4 AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='1erINF61-85' 	--PRIORIDAD_54
or [Eco_N3]=@Eco_N3 AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='1erINF61-85' 	--PRIORIDAD_55
or [Eco_N2]=@Eco_N2 AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='1erINF61-85' 	--PRIORIDAD_56
or [Eco_N1]=@Eco_N1 AND @Familia_APG is NULL  and @Genero_APG is NULL and @Especie_APG is NULL and [Fuente]='1erINF61-85' 	--PRIORIDAD_57

Group by 
[Clave_ecuacion]
,Num_Observaciones
,R2
,[Clave_UMAFOR]
,[Nombre_APG]
,[Genero_APG]
,[Eco_N4]
,[Eco_N3]
,[Eco_N2]
,[Eco_N1]
,[Familia_APG]
,[idEstado]
,[Fuente]

Order by Prioridad_Arbol ASC
,MAX(Num_Observaciones) DESC
,MAX(R2) DESC