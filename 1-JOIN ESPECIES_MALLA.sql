/****** Script for SelectTopNRows command from SSMS  ******/
SELECT DISTINCT
ecoRegiones.[IdConglome]
,especiesXcgl.Anio
,especiesXcgl.[IdEstado]
,especiesXcgl.[Estado]
,especiesXcgl.[Familia_APG]  AS 'Familia_APG'
,especiesXcgl.[Genero_APG]  AS 'Genero_APG'
,especiesXcgl.[Especie_APG]  AS 'Especie_APG'
,especiesXcgl.[Categoria_Infra_APG]  AS 'Categoria_Infra_APG'
,especiesXcgl.[Infra_APG]  AS 'Infra_APG'
,especiesXcgl.[NombreCientifico_APG]  AS 'NombreCientifico_APG'
--::::::::::::::::::::::::::::::::::::::::::::::
,ecoRegiones.[CVE_UMAF_E] as Clave_UMAFOR
,ecoRegiones.[CVEECON1] as Eco_N1
,ecoRegiones.[CVEECON2] as Eco_N2
,ecoRegiones.[CVEECON3] as Eco_N3
,ecoRegiones.[CVEECON4] as Eco_N4

FROM [Taxodium].[dbo].[Especies_X_Conglomerado_muestreo] especiesXcgl
JOIN [Taxodium].[dbo].[UMAF_Ecorregiones_muestreo] ecoRegiones ON (ecoRegiones.[IdConglome] = especiesXcgl.[IdConglomerado])
WHERE Sitio=1
ORDER BY
IdConglome