USE ArbolDesiciones
go
ALTER FUNCTION udfAsignacionEcuacion
--parametros
( 
 @Familia_APG nvarchar(256)
 ,@Genero_APG nvarchar(255)
 ,@Especie_APG nvarchar(255)
 ,@Nombre_APG nvarchar(255)
 ,@Clave_UMAFOR int
 ,@IdEstado int
 ,@Eco_N4 nvarchar(255)
 ,@Eco_N3 nvarchar(255)
 ,@Eco_N2 float
 ,@Eco_N1 int
 


) 

RETURNS @ArboladoEcuaciones TABLE --retorna una variable tipo tabla
(      

          CveEcuacion nvarchar(255)
         ,Num_Observaciones int
         ,R2 float
        ,Prioridad_Arbol nvarchar(10)
        ,Fuente nvarchar(255)
)

AS
BEGIN

 INSERT INTO @ArboladoEcuaciones     --inserta el resultado de una consulta
 SELECT top 1
 [Clave_ecuacion],
 Num_Observaciones,
 R2,
--
(CASE 

        --NIVEL_1
        WHEN  [Nombre_APG] =@Nombre_APG AND [Clave_UMAFOR]=@Clave_UMAFOR  AND [Fuente]='Siplafor' AND @Especie_APG IS NOT NULL  THEN  1
        WHEN [Nombre_APG] =@Nombre_APG AND [idEstado]=@IdEstado AND [Fuente]='Siplafor' AND @Especie_APG IS NOT NULL  THEN  2
        WHEN [Nombre_APG] =@Nombre_APG AND [idEstado]=@IdEstado AND [Eco_N4]=@Eco_N4 AND [Fuente]='1erINF61-85' AND @Especie_APG IS NOT NULL  THEN  3
        WHEN [Nombre_APG] =@Nombre_APG AND [idEstado]=@IdEstado AND [Fuente]='1erINF61-85' AND @Especie_APG IS NOT NULL   THEN  4
        --NIVEL_2
        WHEN [Nombre_APG] =@Nombre_APG AND [Eco_N4]=@Eco_N4 AND [Fuente]='Siplafor'   AND @Especie_APG IS NOT NULL THEN  5
        WHEN [Nombre_APG] =@Nombre_APG AND [Eco_N3]=@Eco_N3 AND [Fuente]='Siplafor'   AND @Especie_APG IS NOT NULL THEN  6
        WHEN [Nombre_APG] =@Nombre_APG AND [Eco_N2]=@Eco_N2 AND [Fuente]='Siplafor'   AND @Especie_APG IS NOT NULL THEN  7
        WHEN [Nombre_APG] =@Nombre_APG AND [Eco_N1]=@Eco_N1 AND [Fuente]='Siplafor'   AND @Especie_APG IS NOT NULL THEN  8
        WHEN [Nombre_APG] =@Nombre_APG AND [Eco_N4]=@Eco_N4 AND [Fuente]='1erINF61-85'  AND @Especie_APG IS NOT NULL THEN  9
        WHEN [Nombre_APG] =@Nombre_APG AND [Eco_N3]=@Eco_N3 AND [Fuente]='1erINF61-85'  AND @Especie_APG IS NOT NULL THEN  10
        WHEN [Nombre_APG] =@Nombre_APG AND [Eco_N2]=@Eco_N2 AND [Fuente]='1erINF61-85'  AND @Especie_APG IS NOT NULL THEN  11
        WHEN [Nombre_APG] =@Nombre_APG AND [Eco_N1]=@Eco_N1 AND [Fuente]='1erINF61-85'  AND @Especie_APG IS NOT NULL THEN  12

--GENERO
        --NIVEL_3
        WHEN [Nombre_APG] =@Genero_APG AND [Clave_UMAFOR]=@Clave_UMAFOR  AND [Fuente]='Siplafor'  THEN  13
        WHEN [Nombre_APG] =@Genero_APG AND [idEstado]=@IdEstado AND [Clave_UMAFOR] IS NULL AND [Fuente]='Siplafor'   THEN  14

        WHEN [Nombre_APG] =@Genero_APG AND [idEstado]=@IdEstado AND [Clave_UMAFOR] IS NULL AND [Eco_N4]=@Eco_N4 AND [Fuente]='1erINF61-85'   THEN  15
        WHEN [Nombre_APG] =@Genero_APG AND [idEstado]=@IdEstado AND [Clave_UMAFOR] IS NULL AND [Fuente]='1erINF61-85'    THEN  16
        --NIVEL_4
        WHEN [Nombre_APG] =@Genero_APG AND [Eco_N4]=@Eco_N4 AND [Fuente]='Siplafor'   THEN  17
        WHEN [Nombre_APG] =@Genero_APG AND [Eco_N3]=@Eco_N3 AND [Fuente]='Siplafor'   THEN  18
        WHEN [Nombre_APG] =@Genero_APG AND [Eco_N2]=@Eco_N2 AND [Fuente]='Siplafor'   THEN  19
        WHEN [Nombre_APG] =@Genero_APG AND [Eco_N1]=@Eco_N1 AND [Fuente]='Siplafor'   THEN  20
        WHEN [Nombre_APG] =@Genero_APG AND [Eco_N4]=@Eco_N4 AND [Fuente]='1erINF61-85'  THEN  21
        WHEN [Nombre_APG] =@Genero_APG AND [Eco_N3]=@Eco_N3 AND [Fuente]='1erINF61-85'  THEN  22
        WHEN [Nombre_APG] =@Genero_APG AND [Eco_N2]=@Eco_N2 AND [Fuente]='1erINF61-85'  THEN  23
        WHEN [Nombre_APG] =@Genero_APG AND [Eco_N1]=@Eco_N1 AND [Fuente]='1erINF61-85'  THEN  24
--FAMILIA
        --NIVEL_5
        WHEN @Familia_APG IS NOT NULL AND[Familia_APG] =@Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Clave_UMAFOR]=@Clave_UMAFOR  AND [Fuente]='Siplafor'   THEN  25
        WHEN @Familia_APG IS NOT NULL AND[Familia_APG] =@Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [idEstado]=@IdEstado AND [Clave_UMAFOR] IS NULL AND [Fuente]='Siplafor'  THEN  26
        WHEN @Familia_APG IS NOT NULL AND[Familia_APG] =@Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [idEstado]=@IdEstado AND [Clave_UMAFOR] IS NULL AND [Eco_N4]=@Eco_N4 AND [Fuente]='1erINF61-85'  THEN  27
        WHEN @Familia_APG IS NOT NULL AND[Familia_APG] =@Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [idEstado]=@IdEstado AND [Clave_UMAFOR] IS NULL AND [Fuente]='1erINF61-85'   THEN  28
        --NIVEL_6
        WHEN @Familia_APG IS NOT NULL AND [Familia_APG] = @Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL  AND [Eco_N4]=@Eco_N4 AND [Fuente]='Siplafor'  THEN  29
        WHEN @Familia_APG IS NOT NULL AND [Familia_APG] = @Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL  AND [Eco_N3]=@Eco_N3 AND [Fuente]='Siplafor'  THEN  30
        WHEN @Familia_APG IS NOT NULL AND [Familia_APG] = @Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL  AND [Eco_N2]=@Eco_N2 AND [Fuente]='Siplafor'  THEN  31
        WHEN @Familia_APG IS NOT NULL AND [Familia_APG] = @Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL  AND [Eco_N1]=@Eco_N1 AND [Fuente]='Siplafor'  THEN  32
        WHEN @Familia_APG IS NOT NULL AND [Familia_APG] = @Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL  AND [Eco_N4]=@Eco_N4 AND [Fuente]='1erINF61-85'   THeN 33
        WHEN @Familia_APG IS NOT NULL AND [Familia_APG] = @Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL  AND [Eco_N3]=@Eco_N3 AND [Fuente]='1erINF61-85'   THEN  34
        WHEN @Familia_APG IS NOT NULL AND [Familia_APG] = @Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL  AND [Eco_N2]=@Eco_N2 AND [Fuente]='1erINF61-85'   THEN  35
        WHEN @Familia_APG IS NOT NULL AND [Familia_APG] = @Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL  AND [Eco_N1]=@Eco_N1 AND [Fuente]='1erINF61-85'   THEN  36
--Afinidad GENERO
        --NIVEL_7
        WHEN @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [Clave_UMAFOR]=@Clave_UMAFOR  AND [Fuente]='Siplafor'  THEN  37
        WHEN @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [idEstado]=@IdEstado AND [Fuente]='Siplafor'   THEN  38
        WHEN @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [idEstado]=@IdEstado AND [Fuente]='1erINF61-85'    THEN  39
        --NIVEL_8
        WHEN @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [Eco_N4]=@Eco_N4 AND [Fuente]='Siplafor'   THEN  40
        WHEN @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [Eco_N3]=@Eco_N3 AND [Fuente]='Siplafor'   THEN  41
        WHEN @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [Eco_N2]=@Eco_N2 AND [Fuente]='Siplafor'   THEN  42
        WHEN @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [Eco_N1]=@Eco_N1 AND [Fuente]='Siplafor'   THEN  43
        WHEN @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [Eco_N4]=@Eco_N4 AND [Fuente]='1erINF61-85'  THEN  44
        WHEN @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [Eco_N3]=@Eco_N3 AND [Fuente]='1erINF61-85'  THEN  45
        WHEN @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [Eco_N2]=@Eco_N2 AND [Fuente]='1erINF61-85'  THEN  46
        WHEN @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [Eco_N1]=@Eco_N1 AND [Fuente]='1erINF61-85'  THEN  47
--RESTO ESPECIES
        --NIVEL_9
        WHEN [Clave_UMAFOR]=@Clave_UMAFOR AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL  AND [Fuente]='Siplafor'   THEN  48
        WHEN [idEstado]=@IdEstado AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='Siplafor'  THEN  49
        WHEN [idEstado]=@IdEstado AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='1erINF61-85'   THEN  50
        --NIVEL_10
        WHEN [Eco_N4]=@Eco_N4 AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='Siplafor'  THEN  51
        WHEN [Eco_N3]=@Eco_N3 AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='Siplafor'  THEN  52
        WHEN [Eco_N2]=@Eco_N2 AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='Siplafor'  THEN  53
        WHEN [Eco_N1]=@Eco_N1 AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='Siplafor'  THEN  54
        WHEN [Eco_N4]=@Eco_N4 AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='1erINF61-85'   THeN 55
        WHEN [Eco_N3]=@Eco_N3 AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='1erINF61-85'   THEN  56
        WHEN [Eco_N2]=@Eco_N2 AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='1erINF61-85'   THEN  57
        WHEN [Eco_N1]=@Eco_N1 AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='1erINF61-85'   THEN  58
        /**/
        end) as 'Prioridad_Arbol'
        ,[Fuente]
--
FROM 
[ArbolDesiciones].[dbo].[Ecuaciones_VRTA_2]
WHERE 
@Nombre_APG != 'ZZ_Desconocido' AND
--ESPECIES
        --NIVEL_1
   @Especie_APG IS NOT NULL AND [Nombre_APG] =@Nombre_APG AND [Clave_UMAFOR]=@Clave_UMAFOR  AND [Fuente]='Siplafor'     AND  @Especie_APG IS NOT NULL--PRIORIDAD_1
OR @Especie_APG IS NOT NULL AND [Nombre_APG] =@Nombre_APG AND [idEstado]=@IdEstado AND [Fuente]='Siplafor'    AND  @Especie_APG IS NOT NULL--PRIORIDAD_2
OR @Especie_APG IS NOT NULL AND [Nombre_APG] =@Nombre_APG AND [idEstado]=@IdEstado AND [Eco_N4]=@Eco_N4 AND [Fuente]='1erINF61-85'    AND  @Especie_APG IS NOT NULL--PRIORIDAD_3
OR @Especie_APG IS NOT NULL AND [Nombre_APG] =@Nombre_APG AND [idEstado]=@IdEstado AND [Fuente]='1erINF61-85'     AND  @Especie_APG IS NOT NULL--PRIORIDAD_4
        --NIVEL_2
OR @Especie_APG IS NOT NULL AND [Nombre_APG] =@Nombre_APG AND [Eco_N4]=@Eco_N4 AND [Fuente]='Siplafor'   AND  @Especie_APG IS NOT NULL --PRIORIDAD_5
OR @Especie_APG IS NOT NULL AND [Nombre_APG] =@Nombre_APG AND [Eco_N3]=@Eco_N3 AND [Fuente]='Siplafor'   AND  @Especie_APG IS NOT NULL --PRIORIDAD_6
OR @Especie_APG IS NOT NULL AND [Nombre_APG] =@Nombre_APG AND [Eco_N2]=@Eco_N2 AND [Fuente]='Siplafor'   AND  @Especie_APG IS NOT NULL --PRIORIDAD_7
OR @Especie_APG IS NOT NULL AND [Nombre_APG] =@Nombre_APG AND [Eco_N1]=@Eco_N1 AND [Fuente]='Siplafor'   AND  @Especie_APG IS NOT NULL --PRIORIDAD_8
OR @Especie_APG IS NOT NULL AND [Nombre_APG] =@Nombre_APG AND [Eco_N4]=@Eco_N4 AND [Fuente]='1erINF61-85'  AND  @Especie_APG IS NOT NULL --PRIORIDAD_9
OR @Especie_APG IS NOT NULL AND [Nombre_APG] =@Nombre_APG AND [Eco_N3]=@Eco_N3 AND [Fuente]='1erINF61-85'  AND  @Especie_APG IS NOT NULL --PRIORIDAD_10
OR @Especie_APG IS NOT NULL AND [Nombre_APG] =@Nombre_APG AND [Eco_N2]=@Eco_N2 AND [Fuente]='1erINF61-85'  AND  @Especie_APG IS NOT NULL --PRIORIDAD_11
OR @Especie_APG IS NOT NULL AND [Nombre_APG] =@Nombre_APG AND [Eco_N1]=@Eco_N1 AND [Fuente]='1erINF61-85'  AND  @Especie_APG IS NOT NULL --PRIORIDAD_12

--GENERO
        --NIVEL_3
OR @Genero_APG IS NOT NULL AND [Nombre_APG] =@Genero_APG AND [Clave_UMAFOR]=@Clave_UMAFOR  AND [Fuente]='Siplafor'  --PRIORIDAD_13
OR @Genero_APG IS NOT NULL AND [Nombre_APG] =@Genero_APG AND [idEstado]=@IdEstado AND [Clave_UMAFOR] IS NULL AND [Eco_N4]=@Eco_N4 AND [Fuente]='1erINF61-85'   --PRIORIDAD_14
OR @Genero_APG IS NOT NULL AND [Nombre_APG] =@Genero_APG AND [idEstado]=@IdEstado AND [Clave_UMAFOR] IS NULL AND [Fuente]='1erINF61-85'    --PRIORIDAD_15
        --NIVEL_4
OR @Genero_APG IS NOT NULL AND [Nombre_APG] =@Genero_APG AND [Eco_N4]=@Eco_N4 AND [Fuente]='Siplafor'   --PRIORIDAD_16
OR @Genero_APG IS NOT NULL AND [Nombre_APG] =@Genero_APG AND [Eco_N3]=@Eco_N3 AND [Fuente]='Siplafor'   --PRIORIDAD_17
OR @Genero_APG IS NOT NULL AND [Nombre_APG] =@Genero_APG AND [Eco_N2]=@Eco_N2 AND [Fuente]='Siplafor'   --PRIORIDAD_18
OR @Genero_APG IS NOT NULL AND [Nombre_APG] =@Genero_APG AND [Eco_N1]=@Eco_N1 AND [Fuente]='Siplafor'   --PRIORIDAD_19
OR @Genero_APG IS NOT NULL AND [Nombre_APG] =@Genero_APG AND [Eco_N4]=@Eco_N4 AND [Fuente]='1erINF61-85'  --PRIORIDAD_20
OR @Genero_APG IS NOT NULL AND [Nombre_APG] =@Genero_APG AND [Eco_N3]=@Eco_N3 AND [Fuente]='1erINF61-85'  --PRIORIDAD_21
OR @Genero_APG IS NOT NULL AND [Nombre_APG] =@Genero_APG AND [Eco_N2]=@Eco_N2 AND [Fuente]='1erINF61-85'  --PRIORIDAD_22
OR @Genero_APG IS NOT NULL AND [Nombre_APG] =@Genero_APG AND [Eco_N1]=@Eco_N1 AND [Fuente]='1erINF61-85'  --PRIORIDAD_23
--FAMILIA
        --NIVEL_5
OR @Familia_APG IS NOT NULL AND [Familia_APG]  =@Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Clave_UMAFOR]=@Clave_UMAFOR  AND [Fuente]='Siplafor'  AND @Nombre_APG != 'ZZ_Desconocido' --PRIORIDAD_24
OR @Familia_APG IS NOT NULL AND [Familia_APG]  =@Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [idEstado]=@IdEstado AND [Clave_UMAFOR] IS NULL AND [Fuente]='Siplafor'   AND @Nombre_APG != 'ZZ_Desconocido' --PRIORIDAD_25
OR @Familia_APG IS NOT NULL AND [Familia_APG]  =@Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [idEstado]=@IdEstado AND [Clave_UMAFOR] IS NULL AND [Eco_N4]=@Eco_N4 AND [Fuente]='1erINF61-85'   AND @Nombre_APG != 'ZZ_Desconocido' --PRIORIDAD_26
OR @Familia_APG IS NOT NULL AND [Familia_APG]  =@Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [idEstado]=@IdEstado AND [Clave_UMAFOR] IS NULL AND [Fuente]='1erINF61-85'    AND @Nombre_APG != 'ZZ_Desconocido' --PRIORIDAD_27
        --NIVEL_6
OR @Familia_APG IS NOT NULL AND [Familia_APG]  =@Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Eco_N4]=@Eco_N4 AND [Fuente]='Siplafor'   AND @Nombre_APG != 'ZZ_Desconocido' --PRIORIDAD_28
OR @Familia_APG IS NOT NULL AND [Familia_APG]  =@Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Eco_N3]=@Eco_N3 AND [Fuente]='Siplafor'   AND @Nombre_APG != 'ZZ_Desconocido' --PRIORIDAD_29
OR @Familia_APG IS NOT NULL AND [Familia_APG]  =@Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Eco_N2]=@Eco_N2 AND [Fuente]='Siplafor'   AND @Nombre_APG != 'ZZ_Desconocido' --PRIORIDAD_30
OR @Familia_APG IS NOT NULL AND [Familia_APG]  =@Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Eco_N1]=@Eco_N1 AND [Fuente]='Siplafor'   AND @Nombre_APG != 'ZZ_Desconocido' --PRIORIDAD_31
OR @Familia_APG IS NOT NULL AND [Familia_APG]  =@Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Eco_N4]=@Eco_N4 AND [Fuente]='1erINF61-85'  AND @Nombre_APG != 'ZZ_Desconocido' --PRIORIDAD_32
OR @Familia_APG IS NOT NULL AND [Familia_APG]  =@Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Eco_N3]=@Eco_N3 AND [Fuente]='1erINF61-85'  AND @Nombre_APG != 'ZZ_Desconocido' --PRIORIDAD_33
OR @Familia_APG IS NOT NULL AND [Familia_APG]  =@Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Eco_N2]=@Eco_N2 AND [Fuente]='1erINF61-85'  AND @Nombre_APG != 'ZZ_Desconocido' --PRIORIDAD_34
OR @Familia_APG IS NOT NULL AND [Familia_APG]  =@Familia_APG AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Eco_N1]=@Eco_N1 AND [Fuente]='1erINF61-85'  AND @Nombre_APG != 'ZZ_Desconocido' --PRIORIDAD_35
--Afinidad GENERO
        --NIVEL_7
OR @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [Clave_UMAFOR]=@Clave_UMAFOR  AND [Fuente]='Siplafor' AND @Nombre_APG != 'ZZ_Desconocido'  --PRIORIDAD_36
OR @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [idEstado]=@IdEstado AND [Fuente]='Siplafor'  AND @Nombre_APG != 'ZZ_Desconocido'  --PRIORIDAD_37
OR @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [idEstado]=@IdEstado AND [Fuente]='1erINF61-85'   AND @Nombre_APG != 'ZZ_Desconocido'  --PRIORIDAD_38
        --NIVEL_8
OR @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [Eco_N4]=@Eco_N4 AND [Fuente]='Siplafor'   --PRIORIDAD_39
OR @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [Eco_N3]=@Eco_N3 AND [Fuente]='Siplafor'   --PRIORIDAD_40
OR @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [Eco_N2]=@Eco_N2 AND [Fuente]='Siplafor'   --PRIORIDAD_41
OR @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [Eco_N1]=@Eco_N1 AND [Fuente]='Siplafor'   --PRIORIDAD_42
OR @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [Eco_N4]=@Eco_N4 AND [Fuente]='1erINF61-85'  --PRIORIDAD_43
OR @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [Eco_N3]=@Eco_N3 AND [Fuente]='1erINF61-85'  --PRIORIDAD_44
OR @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [Eco_N2]=@Eco_N2 AND [Fuente]='1erINF61-85'  --PRIORIDAD_45
OR @Genero_APG IS NOT NULL AND [Genero_APG] =@Genero_APG AND [Eco_N1]=@Eco_N1 AND [Fuente]='1erINF61-85'  --PRIORIDAD_46
--RESTO ESPECIES
        --NIVEL_9
OR [Clave_UMAFOR]=@Clave_UMAFOR  AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='Siplafor'   --PRIORIDAD_47
OR [idEstado]=@IdEstado AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='Siplafor'  --PRIORIDAD_48
OR [idEstado]=@IdEstado AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='1erINF61-85'   --PRIORIDAD_49
        --NIVEL_10
OR [Eco_N4]=@Eco_N4 AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='Siplafor'  --PRIORIDAD_50
OR [Eco_N3]=@Eco_N3 AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='Siplafor'  --PRIORIDAD_51
OR [Eco_N2]=@Eco_N2 AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='Siplafor'  --PRIORIDAD_52
OR [Eco_N1]=@Eco_N1 AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='Siplafor'  --PRIORIDAD_53
OR [Eco_N4]=@Eco_N4 AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='1erINF61-85'   --PRIORIDAD_54
OR [Eco_N3]=@Eco_N3 AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='1erINF61-85'   --PRIORIDAD_55
OR [Eco_N2]=@Eco_N2 AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='1erINF61-85'   --PRIORIDAD_56
OR [Eco_N1]=@Eco_N1 AND [Familia_APG] IS NULL  AND [Genero_APG] IS NULL AND [Especie_APG] IS NULL AND [Fuente]='1erINF61-85'   --PRIORIDAD_57

Group by 
[Clave_ecuacion]
,Num_Observaciones
,R2
,[Clave_UMAFOR]
,[Nombre_APG]
,[Especie_APG]
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
RETURN
END