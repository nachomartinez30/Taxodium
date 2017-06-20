SELECT
       [Conglomerado]
      ,[IdEstado]
      ,[Clave_UMAFOR]
      ,[Familia_APG]
      ,[Genero_APG]
      ,[Especie_APG]
      ,[Categoria_Infra_APG]
      ,[Infra_APG]
      ,[NombreCientifico_APG]
      ,[Eco_N4]
      ,[Eco_N3]
      ,[Eco_N2]
      ,[Eco_N1]
	  ,(SELECT  CveEcuacion from udfAsignacionEcuacion(
		Familia_APG,
		Genero_APG,
		Especie_APG,
		[NombreCientifico_APG],
		Clave_UMAFOR,
		IdEstado,
		Eco_N4,
		Eco_N3,
		Eco_N2,
		Eco_N1)
		) AS 'CVE_ECUACION'
	,(SELECT  Prioridad_Arbol from udfAsignacionEcuacion(
		Familia_APG,
		Genero_APG,
		Especie_APG,
		[NombreCientifico_APG],
		Clave_UMAFOR,
		IdEstado,
		Eco_N4,
		Eco_N3,
		Eco_N2,
		Eco_N1)
		) AS 'Prioridad_Arbol'

FROM
[ArbolDesiciones].[dbo].[IdentidadTaxonomicaPorConglomer]