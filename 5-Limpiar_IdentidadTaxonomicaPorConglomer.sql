update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Conglomerado] = null where [Conglomerado] like 'null%'
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Anio] = null where [Anio] like 'null%'
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [IdEstado] = null where [IdEstado] like 'null%'
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Clave_UMAFOR] = null where [Clave_UMAFOR] like 'null%'
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Grupo_UMAFOR] = null where [Grupo_UMAFOR] like 'null%'
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Familia_APG] = null where [Familia_APG] like 'null%'
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Genero_APG] = null where [Genero_APG] like 'null%'
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Especie_APG] = null where [Especie_APG] like 'null%'
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Categoria_Infra_APG] = null where [Categoria_Infra_APG] like 'null%'
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Infra_APG] = null where [Infra_APG] like 'null%'
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [NombreCientifico_APG] = null where [NombreCientifico_APG] like 'null%'
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Eco_N4] = null where [Eco_N4] like 'null%'
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Eco_N3] = null where [Eco_N3] like 'null%'
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Eco_N2] = null where [Eco_N2] like 'null%'
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Eco_N1] = null where [Eco_N1] like 'null%'

update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Conglomerado] =ltrim(rtrim([Conglomerado]))
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Anio] =ltrim(rtrim([Anio]))
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [IdEstado] =ltrim(rtrim([IdEstado]))
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Clave_UMAFOR] =ltrim(rtrim([Clave_UMAFOR]))
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Grupo_UMAFOR] =ltrim(rtrim([Grupo_UMAFOR]))
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Familia_APG] =ltrim(rtrim([Familia_APG]))
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Genero_APG] =ltrim(rtrim([Genero_APG]))
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Especie_APG] =ltrim(rtrim([Especie_APG]))
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Categoria_Infra_APG] =ltrim(rtrim([Categoria_Infra_APG]))
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Infra_APG] =ltrim(rtrim([Infra_APG]))
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [NombreCientifico_APG] =ltrim(rtrim([NombreCientifico_APG]))
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Eco_N4] =ltrim(rtrim([Eco_N4]))
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Eco_N3] =ltrim(rtrim([Eco_N3]))
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Eco_N2] =ltrim(rtrim([Eco_N2]))
update [ArbolDesiciones].dbo.IdentidadTaxonomicaPorConglomerMuestreo set [Eco_N1] =ltrim(rtrim([Eco_N1]))
