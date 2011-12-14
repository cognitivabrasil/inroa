--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = public, pg_catalog;

--
-- Name: atributos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: feb
--

SELECT pg_catalog.setval('atributos_id_seq', 237, true);


--
-- Name: mapeamentocomposto_id_seq; Type: SEQUENCE SET; Schema: public; Owner: feb
--

SELECT pg_catalog.setval('mapeamentocomposto_id_seq', 9, true);


--
-- Name: padraometadados_id_seq; Type: SEQUENCE SET; Schema: public; Owner: feb
--

SELECT pg_catalog.setval('padraometadados_id_seq', 17, true);


--
-- Name: stopwords_id_seq; Type: SEQUENCE SET; Schema: public; Owner: feb
--

SELECT pg_catalog.setval('stopwords_id_seq', 179, true);


--
-- Name: tipomapeamento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: feb
--

SELECT pg_catalog.setval('tipomapeamento_id_seq', 12, true);


--
-- Name: usuarios_id_seq; Type: SEQUENCE SET; Schema: public; Owner: feb
--

SELECT pg_catalog.setval('usuarios_id_seq', 2, true);


--
-- Data for Name: padraometadados; Type: TABLE DATA; Schema: public; Owner: feb
--

INSERT INTO padraometadados VALUES (2, 'lom', 'oai_lom', 'lom');
INSERT INTO padraometadados VALUES (3, 'obaa', 'oai_obaa', 'obaa');
INSERT INTO padraometadados VALUES (1, 'Dublin Core', 'oai_dc', 'dc');


--
-- Data for Name: atributos; Type: TABLE DATA; Schema: public; Owner: feb
--

INSERT INTO atributos VALUES (1, 3, 'Entry');
INSERT INTO atributos VALUES (2, 3, 'Identifier');
INSERT INTO atributos VALUES (3, 3, 'Catalog');
INSERT INTO atributos VALUES (4, 3, 'Title');
INSERT INTO atributos VALUES (5, 3, 'Language');
INSERT INTO atributos VALUES (6, 3, 'Description');
INSERT INTO atributos VALUES (7, 3, 'Keyword');
INSERT INTO atributos VALUES (8, 3, 'Coverage');
INSERT INTO atributos VALUES (9, 3, 'Structure');
INSERT INTO atributos VALUES (10, 3, 'AggregationLevel');
INSERT INTO atributos VALUES (11, 3, 'Version');
INSERT INTO atributos VALUES (12, 3, 'Status');
INSERT INTO atributos VALUES (13, 3, 'Role');
INSERT INTO atributos VALUES (14, 3, 'Entity');
INSERT INTO atributos VALUES (15, 3, 'Date');
INSERT INTO atributos VALUES (16, 3, 'MetaMetadataCatalog');
INSERT INTO atributos VALUES (17, 3, 'MetaMetadataEntry');
INSERT INTO atributos VALUES (18, 3, 'MetaMetadataRole');
INSERT INTO atributos VALUES (19, 3, 'MetaMetadataEntity');
INSERT INTO atributos VALUES (20, 3, 'MetaMetadataDate');
INSERT INTO atributos VALUES (21, 3, 'MetadataSchema');
INSERT INTO atributos VALUES (22, 3, 'MetaMetadataLanguage');
INSERT INTO atributos VALUES (23, 3, 'Format');
INSERT INTO atributos VALUES (24, 3, 'Size');
INSERT INTO atributos VALUES (25, 3, 'Location');
INSERT INTO atributos VALUES (26, 3, 'Type');
INSERT INTO atributos VALUES (27, 3, 'Name');
INSERT INTO atributos VALUES (28, 3, 'MinimumVersion');
INSERT INTO atributos VALUES (29, 3, 'MaximumVersion ');
INSERT INTO atributos VALUES (30, 3, 'InstallationRemarks');
INSERT INTO atributos VALUES (31, 3, 'OtherPlatformRequirements');
INSERT INTO atributos VALUES (32, 3, 'Duration');
INSERT INTO atributos VALUES (33, 3, 'SupportedPlatforms');
INSERT INTO atributos VALUES (34, 3, 'PlatformType');
INSERT INTO atributos VALUES (35, 3, 'SpecificFormat');
INSERT INTO atributos VALUES (36, 3, 'SpecificSize');
INSERT INTO atributos VALUES (37, 3, 'SpecificLocation');
INSERT INTO atributos VALUES (38, 3, 'SpecificType');
INSERT INTO atributos VALUES (39, 3, 'SpecificName');
INSERT INTO atributos VALUES (40, 3, 'SpecificMinimumVersion');
INSERT INTO atributos VALUES (41, 3, 'SpecificMaximumVersion');
INSERT INTO atributos VALUES (42, 3, 'SpecificInstallationRemarks');
INSERT INTO atributos VALUES (43, 3, 'SpecificOtherPlatformRequirements');
INSERT INTO atributos VALUES (44, 3, 'ServiceName');
INSERT INTO atributos VALUES (45, 3, 'ServiceType');
INSERT INTO atributos VALUES (46, 3, 'Provides');
INSERT INTO atributos VALUES (47, 3, 'Essential');
INSERT INTO atributos VALUES (48, 3, 'Protocol');
INSERT INTO atributos VALUES (49, 3, 'OntologyLanguage');
INSERT INTO atributos VALUES (50, 3, 'OntologyLocation');
INSERT INTO atributos VALUES (51, 3, 'ServiceLanguage');
INSERT INTO atributos VALUES (52, 3, 'ServiceLocation');
INSERT INTO atributos VALUES (53, 3, 'InteractivityType');
INSERT INTO atributos VALUES (54, 3, 'LearningResourceType');
INSERT INTO atributos VALUES (55, 3, 'InteractivityLevel');
INSERT INTO atributos VALUES (56, 3, 'SemanticDensity');
INSERT INTO atributos VALUES (57, 3, 'IntendedEndUserRole');
INSERT INTO atributos VALUES (58, 3, 'Context');
INSERT INTO atributos VALUES (59, 3, 'TypicalAgeRange');
INSERT INTO atributos VALUES (60, 3, 'Difficulty');
INSERT INTO atributos VALUES (61, 3, 'TypicalLearningTime');
INSERT INTO atributos VALUES (62, 3, 'EducationalDescription');
INSERT INTO atributos VALUES (63, 3, 'EducationalLanguage');
INSERT INTO atributos VALUES (64, 3, 'LearningContentType');
INSERT INTO atributos VALUES (65, 3, 'Perception');
INSERT INTO atributos VALUES (66, 3, 'Synchronism');
INSERT INTO atributos VALUES (67, 3, 'CoPresence');
INSERT INTO atributos VALUES (68, 3, 'Reciprocity');
INSERT INTO atributos VALUES (69, 3, 'DidacticStrategy');
INSERT INTO atributos VALUES (70, 3, 'Cost');
INSERT INTO atributos VALUES (71, 3, 'CopyrightAndOtherRestrictions');
INSERT INTO atributos VALUES (72, 3, 'RightsDescription');
INSERT INTO atributos VALUES (73, 3, 'Kind');
INSERT INTO atributos VALUES (74, 3, 'ResourceCatalog');
INSERT INTO atributos VALUES (75, 3, 'ResourceEntry');
INSERT INTO atributos VALUES (76, 3, 'ResourceDescription');
INSERT INTO atributos VALUES (77, 3, 'AnnotationEntity');
INSERT INTO atributos VALUES (78, 3, 'AnnotationDate');
INSERT INTO atributos VALUES (79, 3, 'AnnotationDescription');
INSERT INTO atributos VALUES (80, 3, 'Purpose');
INSERT INTO atributos VALUES (81, 3, 'Source');
INSERT INTO atributos VALUES (82, 3, 'TaxonId');
INSERT INTO atributos VALUES (83, 3, 'TaxonEntry');
INSERT INTO atributos VALUES (84, 3, 'ClassificationDescription');
INSERT INTO atributos VALUES (85, 3, 'ClassificationKeyword');
INSERT INTO atributos VALUES (86, 3, 'HasVisual');
INSERT INTO atributos VALUES (87, 3, 'HasAuditory');
INSERT INTO atributos VALUES (88, 3, 'HasText');
INSERT INTO atributos VALUES (89, 3, 'HasTactile');
INSERT INTO atributos VALUES (90, 3, 'DisplayTransformability');
INSERT INTO atributos VALUES (91, 3, 'ControlFlexibility');
INSERT INTO atributos VALUES (92, 3, 'EquivalentResource');
INSERT INTO atributos VALUES (93, 3, 'IsSuplementary');
INSERT INTO atributos VALUES (94, 3, 'AudioDescription');
INSERT INTO atributos VALUES (95, 3, 'AudioDescriptionLanguage');
INSERT INTO atributos VALUES (96, 3, 'AltTextLang');
INSERT INTO atributos VALUES (97, 3, 'LongDescriptionLang');
INSERT INTO atributos VALUES (98, 3, 'ColorAvoidance');
INSERT INTO atributos VALUES (99, 3, 'GraphicAlternative');
INSERT INTO atributos VALUES (100, 3, 'SignLanguage');
INSERT INTO atributos VALUES (101, 3, 'CaptionTypeLanguage');
INSERT INTO atributos VALUES (102, 3, 'CaptionRate');
INSERT INTO atributos VALUES (103, 3, 'SegmentInformation');
INSERT INTO atributos VALUES (104, 3, 'SegmentInformationIdentifier');
INSERT INTO atributos VALUES (105, 3, 'SegmentInformationTitle');
INSERT INTO atributos VALUES (106, 3, 'SegmentInformationDescription');
INSERT INTO atributos VALUES (107, 3, 'SegmentInformationKeyword');
INSERT INTO atributos VALUES (108, 3, 'SegmentMediaType');
INSERT INTO atributos VALUES (109, 3, 'Start');
INSERT INTO atributos VALUES (110, 3, 'End');
INSERT INTO atributos VALUES (111, 3, 'SegmentGroupInformationIdentifier');
INSERT INTO atributos VALUES (112, 3, 'GroupType');
INSERT INTO atributos VALUES (113, 3, 'SegmentGroupInformationTitle');
INSERT INTO atributos VALUES (114, 3, 'SegmentGroupInformationDescription');
INSERT INTO atributos VALUES (115, 3, 'SegmentGroupInformationKeyword');
INSERT INTO atributos VALUES (116, 3, 'SegmentsIdentifier');
INSERT INTO atributos VALUES (118, 1, 'Title');
INSERT INTO atributos VALUES (119, 1, 'Language');
INSERT INTO atributos VALUES (120, 1, 'Description');
INSERT INTO atributos VALUES (121, 1, 'Subject');
INSERT INTO atributos VALUES (122, 1, 'Coverage');
INSERT INTO atributos VALUES (123, 1, 'Type');
INSERT INTO atributos VALUES (126, 1, 'OtherContributor');
INSERT INTO atributos VALUES (127, 1, 'Publisher');
INSERT INTO atributos VALUES (128, 1, 'Format');
INSERT INTO atributos VALUES (129, 1, 'Rights');
INSERT INTO atributos VALUES (130, 1, 'Relation');
INSERT INTO atributos VALUES (131, 1, 'Source');
INSERT INTO atributos VALUES (132, 2, 'Identifier');
INSERT INTO atributos VALUES (133, 2, 'Catalog');
INSERT INTO atributos VALUES (134, 2, 'Entry');
INSERT INTO atributos VALUES (135, 2, 'Title');
INSERT INTO atributos VALUES (136, 2, 'Language');
INSERT INTO atributos VALUES (137, 2, 'Description');
INSERT INTO atributos VALUES (138, 2, 'Keyword');
INSERT INTO atributos VALUES (139, 2, 'Coverage');
INSERT INTO atributos VALUES (140, 2, 'Structure');
INSERT INTO atributos VALUES (141, 2, 'AggregationLevel');
INSERT INTO atributos VALUES (142, 2, 'Version');
INSERT INTO atributos VALUES (143, 2, 'Status');
INSERT INTO atributos VALUES (144, 2, 'Role');
INSERT INTO atributos VALUES (145, 2, 'Entity');
INSERT INTO atributos VALUES (146, 2, 'Date');
INSERT INTO atributos VALUES (147, 2, 'MetaMetadataCatalog');
INSERT INTO atributos VALUES (148, 2, 'MetaMetadataEntry');
INSERT INTO atributos VALUES (149, 2, 'MetaMetadataRole');
INSERT INTO atributos VALUES (150, 2, 'MetaMetadataEntity');
INSERT INTO atributos VALUES (151, 2, 'MetaMetadataDate');
INSERT INTO atributos VALUES (124, 1, 'Date');
INSERT INTO atributos VALUES (125, 1, 'Creator');
INSERT INTO atributos VALUES (152, 2, 'MetadataSchema');
INSERT INTO atributos VALUES (153, 2, 'MetaMetadataLanguage');
INSERT INTO atributos VALUES (154, 2, 'Format');
INSERT INTO atributos VALUES (155, 2, 'Size');
INSERT INTO atributos VALUES (156, 2, 'Location');
INSERT INTO atributos VALUES (157, 2, 'Type');
INSERT INTO atributos VALUES (158, 2, 'Name');
INSERT INTO atributos VALUES (159, 2, 'MinimumVersion');
INSERT INTO atributos VALUES (160, 2, 'MaximumVersion ');
INSERT INTO atributos VALUES (161, 2, 'InstallationRemarks');
INSERT INTO atributos VALUES (162, 2, 'OtherPlatformRequirements');
INSERT INTO atributos VALUES (163, 2, 'Duration');
INSERT INTO atributos VALUES (164, 2, 'InteractivityType');
INSERT INTO atributos VALUES (165, 2, 'LearningResourceType');
INSERT INTO atributos VALUES (166, 2, 'InteractivityLevel');
INSERT INTO atributos VALUES (167, 2, 'SemanticDensity');
INSERT INTO atributos VALUES (168, 2, 'IntendedEndUserRole');
INSERT INTO atributos VALUES (169, 2, 'Context');
INSERT INTO atributos VALUES (170, 2, 'TypicalAgeRange');
INSERT INTO atributos VALUES (171, 2, 'Difficulty');
INSERT INTO atributos VALUES (172, 2, 'TypicalLearningTime');
INSERT INTO atributos VALUES (173, 2, 'EducationalDescription');
INSERT INTO atributos VALUES (174, 2, 'EducationalLanguage');
INSERT INTO atributos VALUES (175, 2, 'Cost');
INSERT INTO atributos VALUES (176, 2, 'CopyrightAndOtherRestrictions');
INSERT INTO atributos VALUES (177, 2, 'RightsDescription');
INSERT INTO atributos VALUES (178, 2, 'Kind');
INSERT INTO atributos VALUES (179, 2, 'ResourceCatalog');
INSERT INTO atributos VALUES (180, 2, 'ResourceEntry');
INSERT INTO atributos VALUES (181, 2, 'ResourceDescription');
INSERT INTO atributos VALUES (182, 2, 'AnnotationEntity');
INSERT INTO atributos VALUES (183, 2, 'AnnotationDate');
INSERT INTO atributos VALUES (184, 2, 'AnnotationDescription');
INSERT INTO atributos VALUES (185, 2, 'Purpose');
INSERT INTO atributos VALUES (186, 2, 'Source');
INSERT INTO atributos VALUES (187, 2, 'TaxonId');
INSERT INTO atributos VALUES (188, 2, 'TaxonEntry');
INSERT INTO atributos VALUES (189, 2, 'ClassificationDescription');
INSERT INTO atributos VALUES (190, 2, 'ClassificationKeyword');
INSERT INTO atributos VALUES (117, 1, 'Identifier');
INSERT INTO atributos VALUES (0, 3, 'Defina um mapeamento');


--
-- Data for Name: mapeamentocomposto; Type: TABLE DATA; Schema: public; Owner: feb
--

INSERT INTO mapeamentocomposto VALUES (1, 'Autor', 13);
INSERT INTO mapeamentocomposto VALUES (2, 'Outro Contribuinte', 13);
INSERT INTO mapeamentocomposto VALUES (3, 'Publisher', 13);


--
-- Data for Name: tipomapeamento; Type: TABLE DATA; Schema: public; Owner: feb
--

INSERT INTO tipomapeamento VALUES (1, 'Padrão', 'Mapeamento Padrão');


--
-- Data for Name: mapeamentos; Type: TABLE DATA; Schema: public; Owner: feb
--

INSERT INTO mapeamentos VALUES (23, 1, 117, 2, 1, NULL);
INSERT INTO mapeamentos VALUES (238, 2, 134, 1, 1, NULL);
INSERT INTO mapeamentos VALUES (239, 2, 132, 2, 1, NULL);
INSERT INTO mapeamentos VALUES (240, 2, 133, 3, 1, NULL);
INSERT INTO mapeamentos VALUES (241, 2, 135, 4, 1, NULL);
INSERT INTO mapeamentos VALUES (242, 2, 136, 5, 1, NULL);
INSERT INTO mapeamentos VALUES (243, 2, 137, 6, 1, NULL);
INSERT INTO mapeamentos VALUES (24, 1, 118, 4, 1, NULL);
INSERT INTO mapeamentos VALUES (25, 1, 119, 5, 1, NULL);
INSERT INTO mapeamentos VALUES (26, 1, 120, 6, 1, NULL);
INSERT INTO mapeamentos VALUES (27, 1, 121, 7, 1, NULL);
INSERT INTO mapeamentos VALUES (28, 1, 122, 8, 1, NULL);
INSERT INTO mapeamentos VALUES (29, 1, 123, 54, 1, NULL);
INSERT INTO mapeamentos VALUES (30, 1, 124, 15, 1, NULL);
INSERT INTO mapeamentos VALUES (31, 1, 125, 14, 1, 1);
INSERT INTO mapeamentos VALUES (32, 1, 126, 14, 1, 2);
INSERT INTO mapeamentos VALUES (33, 1, 127, 14, 1, 3);
INSERT INTO mapeamentos VALUES (34, 1, 128, 23, 1, NULL);
INSERT INTO mapeamentos VALUES (35, 1, 129, 72, 1, NULL);
INSERT INTO mapeamentos VALUES (36, 1, 130, 76, 1, NULL);
INSERT INTO mapeamentos VALUES (37, 1, 131, 25, 1, NULL);
INSERT INTO mapeamentos VALUES (244, 2, 138, 7, 1, NULL);
INSERT INTO mapeamentos VALUES (245, 2, 139, 8, 1, NULL);
INSERT INTO mapeamentos VALUES (246, 2, 140, 9, 1, NULL);
INSERT INTO mapeamentos VALUES (247, 2, 141, 10, 1, NULL);
INSERT INTO mapeamentos VALUES (248, 2, 142, 11, 1, NULL);
INSERT INTO mapeamentos VALUES (249, 2, 143, 12, 1, NULL);
INSERT INTO mapeamentos VALUES (250, 2, 144, 13, 1, NULL);
INSERT INTO mapeamentos VALUES (251, 2, 145, 14, 1, NULL);
INSERT INTO mapeamentos VALUES (252, 2, 146, 15, 1, NULL);
INSERT INTO mapeamentos VALUES (122, 3, 1, 1, 1, NULL);
INSERT INTO mapeamentos VALUES (123, 3, 2, 2, 1, NULL);
INSERT INTO mapeamentos VALUES (124, 3, 3, 3, 1, NULL);
INSERT INTO mapeamentos VALUES (125, 3, 4, 4, 1, NULL);
INSERT INTO mapeamentos VALUES (126, 3, 5, 5, 1, NULL);
INSERT INTO mapeamentos VALUES (127, 3, 6, 6, 1, NULL);
INSERT INTO mapeamentos VALUES (128, 3, 7, 7, 1, NULL);
INSERT INTO mapeamentos VALUES (129, 3, 8, 8, 1, NULL);
INSERT INTO mapeamentos VALUES (130, 3, 9, 9, 1, NULL);
INSERT INTO mapeamentos VALUES (131, 3, 10, 10, 1, NULL);
INSERT INTO mapeamentos VALUES (132, 3, 11, 11, 1, NULL);
INSERT INTO mapeamentos VALUES (133, 3, 12, 12, 1, NULL);
INSERT INTO mapeamentos VALUES (134, 3, 13, 13, 1, NULL);
INSERT INTO mapeamentos VALUES (135, 3, 14, 14, 1, NULL);
INSERT INTO mapeamentos VALUES (136, 3, 15, 15, 1, NULL);
INSERT INTO mapeamentos VALUES (137, 3, 16, 16, 1, NULL);
INSERT INTO mapeamentos VALUES (138, 3, 17, 17, 1, NULL);
INSERT INTO mapeamentos VALUES (139, 3, 18, 18, 1, NULL);
INSERT INTO mapeamentos VALUES (140, 3, 19, 19, 1, NULL);
INSERT INTO mapeamentos VALUES (141, 3, 20, 20, 1, NULL);
INSERT INTO mapeamentos VALUES (142, 3, 21, 21, 1, NULL);
INSERT INTO mapeamentos VALUES (143, 3, 22, 22, 1, NULL);
INSERT INTO mapeamentos VALUES (144, 3, 23, 23, 1, NULL);
INSERT INTO mapeamentos VALUES (145, 3, 24, 24, 1, NULL);
INSERT INTO mapeamentos VALUES (146, 3, 25, 25, 1, NULL);
INSERT INTO mapeamentos VALUES (147, 3, 26, 26, 1, NULL);
INSERT INTO mapeamentos VALUES (148, 3, 27, 27, 1, NULL);
INSERT INTO mapeamentos VALUES (149, 3, 28, 28, 1, NULL);
INSERT INTO mapeamentos VALUES (150, 3, 29, 29, 1, NULL);
INSERT INTO mapeamentos VALUES (151, 3, 30, 30, 1, NULL);
INSERT INTO mapeamentos VALUES (152, 3, 31, 31, 1, NULL);
INSERT INTO mapeamentos VALUES (153, 3, 32, 32, 1, NULL);
INSERT INTO mapeamentos VALUES (154, 3, 33, 33, 1, NULL);
INSERT INTO mapeamentos VALUES (155, 3, 34, 34, 1, NULL);
INSERT INTO mapeamentos VALUES (156, 3, 35, 35, 1, NULL);
INSERT INTO mapeamentos VALUES (157, 3, 36, 36, 1, NULL);
INSERT INTO mapeamentos VALUES (158, 3, 37, 37, 1, NULL);
INSERT INTO mapeamentos VALUES (159, 3, 38, 38, 1, NULL);
INSERT INTO mapeamentos VALUES (160, 3, 39, 39, 1, NULL);
INSERT INTO mapeamentos VALUES (161, 3, 40, 40, 1, NULL);
INSERT INTO mapeamentos VALUES (162, 3, 41, 41, 1, NULL);
INSERT INTO mapeamentos VALUES (163, 3, 42, 42, 1, NULL);
INSERT INTO mapeamentos VALUES (164, 3, 43, 43, 1, NULL);
INSERT INTO mapeamentos VALUES (165, 3, 44, 44, 1, NULL);
INSERT INTO mapeamentos VALUES (166, 3, 45, 45, 1, NULL);
INSERT INTO mapeamentos VALUES (167, 3, 46, 46, 1, NULL);
INSERT INTO mapeamentos VALUES (168, 3, 47, 47, 1, NULL);
INSERT INTO mapeamentos VALUES (169, 3, 48, 48, 1, NULL);
INSERT INTO mapeamentos VALUES (170, 3, 49, 49, 1, NULL);
INSERT INTO mapeamentos VALUES (171, 3, 50, 50, 1, NULL);
INSERT INTO mapeamentos VALUES (172, 3, 51, 51, 1, NULL);
INSERT INTO mapeamentos VALUES (173, 3, 52, 52, 1, NULL);
INSERT INTO mapeamentos VALUES (253, 2, 147, 16, 1, NULL);
INSERT INTO mapeamentos VALUES (254, 2, 148, 17, 1, NULL);
INSERT INTO mapeamentos VALUES (174, 3, 53, 53, 1, NULL);
INSERT INTO mapeamentos VALUES (175, 3, 54, 54, 1, NULL);
INSERT INTO mapeamentos VALUES (176, 3, 55, 55, 1, NULL);
INSERT INTO mapeamentos VALUES (177, 3, 56, 56, 1, NULL);
INSERT INTO mapeamentos VALUES (178, 3, 57, 57, 1, NULL);
INSERT INTO mapeamentos VALUES (179, 3, 58, 58, 1, NULL);
INSERT INTO mapeamentos VALUES (180, 3, 59, 59, 1, NULL);
INSERT INTO mapeamentos VALUES (181, 3, 60, 60, 1, NULL);
INSERT INTO mapeamentos VALUES (182, 3, 61, 61, 1, NULL);
INSERT INTO mapeamentos VALUES (183, 3, 62, 62, 1, NULL);
INSERT INTO mapeamentos VALUES (184, 3, 63, 63, 1, NULL);
INSERT INTO mapeamentos VALUES (185, 3, 64, 64, 1, NULL);
INSERT INTO mapeamentos VALUES (186, 3, 65, 65, 1, NULL);
INSERT INTO mapeamentos VALUES (187, 3, 66, 66, 1, NULL);
INSERT INTO mapeamentos VALUES (188, 3, 67, 67, 1, NULL);
INSERT INTO mapeamentos VALUES (189, 3, 68, 68, 1, NULL);
INSERT INTO mapeamentos VALUES (190, 3, 69, 69, 1, NULL);
INSERT INTO mapeamentos VALUES (191, 3, 70, 70, 1, NULL);
INSERT INTO mapeamentos VALUES (192, 3, 71, 71, 1, NULL);
INSERT INTO mapeamentos VALUES (193, 3, 72, 72, 1, NULL);
INSERT INTO mapeamentos VALUES (194, 3, 73, 73, 1, NULL);
INSERT INTO mapeamentos VALUES (195, 3, 74, 74, 1, NULL);
INSERT INTO mapeamentos VALUES (196, 3, 75, 75, 1, NULL);
INSERT INTO mapeamentos VALUES (197, 3, 76, 76, 1, NULL);
INSERT INTO mapeamentos VALUES (198, 3, 77, 77, 1, NULL);
INSERT INTO mapeamentos VALUES (199, 3, 78, 78, 1, NULL);
INSERT INTO mapeamentos VALUES (200, 3, 79, 79, 1, NULL);
INSERT INTO mapeamentos VALUES (201, 3, 80, 80, 1, NULL);
INSERT INTO mapeamentos VALUES (202, 3, 81, 81, 1, NULL);
INSERT INTO mapeamentos VALUES (203, 3, 82, 82, 1, NULL);
INSERT INTO mapeamentos VALUES (204, 3, 83, 83, 1, NULL);
INSERT INTO mapeamentos VALUES (205, 3, 84, 84, 1, NULL);
INSERT INTO mapeamentos VALUES (206, 3, 85, 85, 1, NULL);
INSERT INTO mapeamentos VALUES (207, 3, 86, 86, 1, NULL);
INSERT INTO mapeamentos VALUES (208, 3, 87, 87, 1, NULL);
INSERT INTO mapeamentos VALUES (209, 3, 88, 88, 1, NULL);
INSERT INTO mapeamentos VALUES (210, 3, 89, 89, 1, NULL);
INSERT INTO mapeamentos VALUES (211, 3, 90, 90, 1, NULL);
INSERT INTO mapeamentos VALUES (212, 3, 91, 91, 1, NULL);
INSERT INTO mapeamentos VALUES (213, 3, 92, 92, 1, NULL);
INSERT INTO mapeamentos VALUES (214, 3, 93, 93, 1, NULL);
INSERT INTO mapeamentos VALUES (215, 3, 94, 94, 1, NULL);
INSERT INTO mapeamentos VALUES (216, 3, 95, 95, 1, NULL);
INSERT INTO mapeamentos VALUES (217, 3, 96, 96, 1, NULL);
INSERT INTO mapeamentos VALUES (218, 3, 97, 97, 1, NULL);
INSERT INTO mapeamentos VALUES (219, 3, 98, 98, 1, NULL);
INSERT INTO mapeamentos VALUES (220, 3, 99, 99, 1, NULL);
INSERT INTO mapeamentos VALUES (221, 3, 100, 100, 1, NULL);
INSERT INTO mapeamentos VALUES (222, 3, 101, 101, 1, NULL);
INSERT INTO mapeamentos VALUES (223, 3, 102, 102, 1, NULL);
INSERT INTO mapeamentos VALUES (224, 3, 103, 103, 1, NULL);
INSERT INTO mapeamentos VALUES (225, 3, 104, 104, 1, NULL);
INSERT INTO mapeamentos VALUES (226, 3, 105, 105, 1, NULL);
INSERT INTO mapeamentos VALUES (227, 3, 106, 106, 1, NULL);
INSERT INTO mapeamentos VALUES (228, 3, 107, 107, 1, NULL);
INSERT INTO mapeamentos VALUES (229, 3, 108, 108, 1, NULL);
INSERT INTO mapeamentos VALUES (230, 3, 109, 109, 1, NULL);
INSERT INTO mapeamentos VALUES (231, 3, 110, 110, 1, NULL);
INSERT INTO mapeamentos VALUES (232, 3, 111, 111, 1, NULL);
INSERT INTO mapeamentos VALUES (233, 3, 112, 112, 1, NULL);
INSERT INTO mapeamentos VALUES (234, 3, 113, 113, 1, NULL);
INSERT INTO mapeamentos VALUES (235, 3, 114, 114, 1, NULL);
INSERT INTO mapeamentos VALUES (236, 3, 115, 115, 1, NULL);
INSERT INTO mapeamentos VALUES (237, 3, 116, 116, 1, NULL);
INSERT INTO mapeamentos VALUES (255, 2, 149, 18, 1, NULL);
INSERT INTO mapeamentos VALUES (256, 2, 150, 19, 1, NULL);
INSERT INTO mapeamentos VALUES (257, 2, 151, 20, 1, NULL);
INSERT INTO mapeamentos VALUES (258, 2, 152, 21, 1, NULL);
INSERT INTO mapeamentos VALUES (259, 2, 153, 22, 1, NULL);
INSERT INTO mapeamentos VALUES (260, 2, 154, 23, 1, NULL);
INSERT INTO mapeamentos VALUES (261, 2, 155, 24, 1, NULL);
INSERT INTO mapeamentos VALUES (262, 2, 156, 25, 1, NULL);
INSERT INTO mapeamentos VALUES (263, 2, 157, 26, 1, NULL);
INSERT INTO mapeamentos VALUES (264, 2, 158, 27, 1, NULL);
INSERT INTO mapeamentos VALUES (265, 2, 159, 28, 1, NULL);
INSERT INTO mapeamentos VALUES (266, 2, 160, 29, 1, NULL);
INSERT INTO mapeamentos VALUES (267, 2, 161, 30, 1, NULL);
INSERT INTO mapeamentos VALUES (268, 2, 162, 31, 1, NULL);
INSERT INTO mapeamentos VALUES (269, 2, 163, 32, 1, NULL);
INSERT INTO mapeamentos VALUES (270, 2, 164, 53, 1, NULL);
INSERT INTO mapeamentos VALUES (271, 2, 165, 54, 1, NULL);
INSERT INTO mapeamentos VALUES (272, 2, 166, 55, 1, NULL);
INSERT INTO mapeamentos VALUES (273, 2, 167, 56, 1, NULL);
INSERT INTO mapeamentos VALUES (274, 2, 168, 57, 1, NULL);
INSERT INTO mapeamentos VALUES (275, 2, 169, 58, 1, NULL);
INSERT INTO mapeamentos VALUES (276, 2, 170, 59, 1, NULL);
INSERT INTO mapeamentos VALUES (277, 2, 171, 60, 1, NULL);
INSERT INTO mapeamentos VALUES (278, 2, 172, 61, 1, NULL);
INSERT INTO mapeamentos VALUES (279, 2, 173, 62, 1, NULL);
INSERT INTO mapeamentos VALUES (280, 2, 174, 63, 1, NULL);
INSERT INTO mapeamentos VALUES (281, 2, 175, 70, 1, NULL);
INSERT INTO mapeamentos VALUES (282, 2, 176, 71, 1, NULL);
INSERT INTO mapeamentos VALUES (283, 2, 177, 72, 1, NULL);
INSERT INTO mapeamentos VALUES (284, 2, 178, 73, 1, NULL);
INSERT INTO mapeamentos VALUES (285, 2, 179, 74, 1, NULL);
INSERT INTO mapeamentos VALUES (286, 2, 180, 75, 1, NULL);
INSERT INTO mapeamentos VALUES (287, 2, 181, 76, 1, NULL);
INSERT INTO mapeamentos VALUES (288, 2, 182, 77, 1, NULL);
INSERT INTO mapeamentos VALUES (289, 2, 183, 78, 1, NULL);
INSERT INTO mapeamentos VALUES (290, 2, 184, 79, 1, NULL);
INSERT INTO mapeamentos VALUES (291, 2, 185, 80, 1, NULL);
INSERT INTO mapeamentos VALUES (292, 2, 186, 81, 1, NULL);
INSERT INTO mapeamentos VALUES (293, 2, 187, 82, 1, NULL);
INSERT INTO mapeamentos VALUES (294, 2, 188, 83, 1, NULL);
INSERT INTO mapeamentos VALUES (295, 2, 189, 84, 1, NULL);
INSERT INTO mapeamentos VALUES (296, 2, 190, 85, 1, NULL);


--
-- Data for Name: stopwords; Type: TABLE DATA; Schema: public; Owner: feb
--

INSERT INTO stopwords VALUES (1, 'a');
INSERT INTO stopwords VALUES (2, 'à');
INSERT INTO stopwords VALUES (3, 'agora');
INSERT INTO stopwords VALUES (4, 'ainda');
INSERT INTO stopwords VALUES (5, 'além');
INSERT INTO stopwords VALUES (6, 'alguns');
INSERT INTO stopwords VALUES (7, 'ano');
INSERT INTO stopwords VALUES (8, 'anos');
INSERT INTO stopwords VALUES (9, 'antes');
INSERT INTO stopwords VALUES (10, 'ao');
INSERT INTO stopwords VALUES (11, 'aos');
INSERT INTO stopwords VALUES (12, 'apenas');
INSERT INTO stopwords VALUES (13, 'após');
INSERT INTO stopwords VALUES (14, 'aqui');
INSERT INTO stopwords VALUES (15, 'as');
INSERT INTO stopwords VALUES (16, 'às');
INSERT INTO stopwords VALUES (17, 'assim');
INSERT INTO stopwords VALUES (18, 'até');
INSERT INTO stopwords VALUES (19, 'bem');
INSERT INTO stopwords VALUES (20, 'bom');
INSERT INTO stopwords VALUES (21, 'cada');
INSERT INTO stopwords VALUES (22, 'caso');
INSERT INTO stopwords VALUES (23, 'coisa');
INSERT INTO stopwords VALUES (24, 'com');
INSERT INTO stopwords VALUES (25, 'como');
INSERT INTO stopwords VALUES (26, 'conta');
INSERT INTO stopwords VALUES (27, 'contra');
INSERT INTO stopwords VALUES (28, 'da');
INSERT INTO stopwords VALUES (29, 'dar');
INSERT INTO stopwords VALUES (30, 'das');
INSERT INTO stopwords VALUES (31, 'de');
INSERT INTO stopwords VALUES (32, 'depois');
INSERT INTO stopwords VALUES (33, 'desde');
INSERT INTO stopwords VALUES (34, 'deve');
INSERT INTO stopwords VALUES (35, 'dia');
INSERT INTO stopwords VALUES (36, 'dias');
INSERT INTO stopwords VALUES (37, 'disse');
INSERT INTO stopwords VALUES (38, 'diz');
INSERT INTO stopwords VALUES (39, 'do');
INSERT INTO stopwords VALUES (40, 'dois');
INSERT INTO stopwords VALUES (41, 'dos');
INSERT INTO stopwords VALUES (42, 'duas');
INSERT INTO stopwords VALUES (43, 'durante');
INSERT INTO stopwords VALUES (44, 'e');
INSERT INTO stopwords VALUES (45, 'é');
INSERT INTO stopwords VALUES (46, 'ela');
INSERT INTO stopwords VALUES (47, 'ele');
INSERT INTO stopwords VALUES (48, 'eles');
INSERT INTO stopwords VALUES (49, 'em');
INSERT INTO stopwords VALUES (50, 'enquanto');
INSERT INTO stopwords VALUES (51, 'então');
INSERT INTO stopwords VALUES (52, 'entre');
INSERT INTO stopwords VALUES (53, 'era');
INSERT INTO stopwords VALUES (54, 'essa');
INSERT INTO stopwords VALUES (55, 'esse');
INSERT INTO stopwords VALUES (56, 'esta');
INSERT INTO stopwords VALUES (57, 'está');
INSERT INTO stopwords VALUES (58, 'estão');
INSERT INTO stopwords VALUES (59, 'estava');
INSERT INTO stopwords VALUES (60, 'este');
INSERT INTO stopwords VALUES (61, 'eu');
INSERT INTO stopwords VALUES (62, 'eua');
INSERT INTO stopwords VALUES (63, 'fato');
INSERT INTO stopwords VALUES (64, 'faz');
INSERT INTO stopwords VALUES (65, 'fazer');
INSERT INTO stopwords VALUES (66, 'fez');
INSERT INTO stopwords VALUES (67, 'ficou');
INSERT INTO stopwords VALUES (68, 'foi');
INSERT INTO stopwords VALUES (69, 'fora');
INSERT INTO stopwords VALUES (70, 'foram');
INSERT INTO stopwords VALUES (71, 'forma');
INSERT INTO stopwords VALUES (72, 'há');
INSERT INTO stopwords VALUES (73, 'havia');
INSERT INTO stopwords VALUES (74, 'hoje');
INSERT INTO stopwords VALUES (75, 'isso');
INSERT INTO stopwords VALUES (76, 'já');
INSERT INTO stopwords VALUES (77, 'lado');
INSERT INTO stopwords VALUES (78, 'maior');
INSERT INTO stopwords VALUES (79, 'mais');
INSERT INTO stopwords VALUES (80, 'mas');
INSERT INTO stopwords VALUES (81, 'me');
INSERT INTO stopwords VALUES (82, 'meio');
INSERT INTO stopwords VALUES (83, 'melhor');
INSERT INTO stopwords VALUES (84, 'menos');
INSERT INTO stopwords VALUES (85, 'meses');
INSERT INTO stopwords VALUES (86, 'mesma');
INSERT INTO stopwords VALUES (87, 'mesmo');
INSERT INTO stopwords VALUES (88, 'meu');
INSERT INTO stopwords VALUES (89, 'minha');
INSERT INTO stopwords VALUES (90, 'muito');
INSERT INTO stopwords VALUES (91, 'na');
INSERT INTO stopwords VALUES (92, 'nada');
INSERT INTO stopwords VALUES (93, 'não');
INSERT INTO stopwords VALUES (94, 'nas');
INSERT INTO stopwords VALUES (95, 'nem');
INSERT INTO stopwords VALUES (96, 'neste');
INSERT INTO stopwords VALUES (97, 'no');
INSERT INTO stopwords VALUES (98, 'nome');
INSERT INTO stopwords VALUES (99, 'nos');
INSERT INTO stopwords VALUES (100, 'nós');
INSERT INTO stopwords VALUES (101, 'nova');
INSERT INTO stopwords VALUES (102, 'novo');
INSERT INTO stopwords VALUES (103, 'num');
INSERT INTO stopwords VALUES (104, 'numa');
INSERT INTO stopwords VALUES (105, 'o');
INSERT INTO stopwords VALUES (106, 'onde');
INSERT INTO stopwords VALUES (107, 'ontem');
INSERT INTO stopwords VALUES (108, 'os');
INSERT INTO stopwords VALUES (109, 'ou');
INSERT INTO stopwords VALUES (110, 'outra');
INSERT INTO stopwords VALUES (111, 'outras');
INSERT INTO stopwords VALUES (112, 'outro');
INSERT INTO stopwords VALUES (113, 'outros');
INSERT INTO stopwords VALUES (114, 'para');
INSERT INTO stopwords VALUES (115, 'parte');
INSERT INTO stopwords VALUES (116, 'partir');
INSERT INTO stopwords VALUES (117, 'pela');
INSERT INTO stopwords VALUES (118, 'pelo');
INSERT INTO stopwords VALUES (119, 'pelos');
INSERT INTO stopwords VALUES (120, 'pode');
INSERT INTO stopwords VALUES (121, 'podem');
INSERT INTO stopwords VALUES (122, 'poder');
INSERT INTO stopwords VALUES (123, 'pontos');
INSERT INTO stopwords VALUES (124, 'por');
INSERT INTO stopwords VALUES (125, 'porque');
INSERT INTO stopwords VALUES (126, 'pouco');
INSERT INTO stopwords VALUES (127, 'qual');
INSERT INTO stopwords VALUES (128, 'qualquer');
INSERT INTO stopwords VALUES (129, 'quando');
INSERT INTO stopwords VALUES (130, 'quanto');
INSERT INTO stopwords VALUES (131, 'quase');
INSERT INTO stopwords VALUES (132, 'quatro');
INSERT INTO stopwords VALUES (133, 'que');
INSERT INTO stopwords VALUES (134, 'quem');
INSERT INTO stopwords VALUES (135, 'quer');
INSERT INTO stopwords VALUES (136, 'r');
INSERT INTO stopwords VALUES (137, 'são');
INSERT INTO stopwords VALUES (138, 'se');
INSERT INTO stopwords VALUES (139, 'segundo');
INSERT INTO stopwords VALUES (140, 'seja');
INSERT INTO stopwords VALUES (141, 'sem');
INSERT INTO stopwords VALUES (142, 'sempre');
INSERT INTO stopwords VALUES (143, 'sendo');
INSERT INTO stopwords VALUES (144, 'ser');
INSERT INTO stopwords VALUES (145, 'será');
INSERT INTO stopwords VALUES (146, 'serão');
INSERT INTO stopwords VALUES (147, 'seria');
INSERT INTO stopwords VALUES (148, 'setor');
INSERT INTO stopwords VALUES (149, 'seu');
INSERT INTO stopwords VALUES (150, 'seus');
INSERT INTO stopwords VALUES (151, 'sido');
INSERT INTO stopwords VALUES (152, 'só');
INSERT INTO stopwords VALUES (153, 'sobre');
INSERT INTO stopwords VALUES (154, 'sua');
INSERT INTO stopwords VALUES (155, 'suas');
INSERT INTO stopwords VALUES (156, 'sul');
INSERT INTO stopwords VALUES (157, 'também');
INSERT INTO stopwords VALUES (158, 'tão');
INSERT INTO stopwords VALUES (159, 'tel');
INSERT INTO stopwords VALUES (160, 'tem');
INSERT INTO stopwords VALUES (161, 'têm');
INSERT INTO stopwords VALUES (162, 'ter');
INSERT INTO stopwords VALUES (163, 'teve');
INSERT INTO stopwords VALUES (164, 'tinha');
INSERT INTO stopwords VALUES (165, 'toda');
INSERT INTO stopwords VALUES (166, 'todas');
INSERT INTO stopwords VALUES (167, 'todo');
INSERT INTO stopwords VALUES (168, 'todos');
INSERT INTO stopwords VALUES (169, 'três');
INSERT INTO stopwords VALUES (170, 'tudo');
INSERT INTO stopwords VALUES (171, 'um');
INSERT INTO stopwords VALUES (172, 'uma');
INSERT INTO stopwords VALUES (173, 'us');
INSERT INTO stopwords VALUES (174, 'vai');
INSERT INTO stopwords VALUES (175, 'vão');
INSERT INTO stopwords VALUES (176, 'vem');
INSERT INTO stopwords VALUES (177, 'vez');
INSERT INTO stopwords VALUES (178, 'vezes');
INSERT INTO stopwords VALUES (179, 'você');


--
-- Data for Name: usuarios; Type: TABLE DATA; Schema: public; Owner: feb
--

INSERT INTO usuarios VALUES (1, 'admin', '698dc19d489c4e4db73e28a713eab07b', 'Administrador da federação');


--
-- PostgreSQL database dump complete
--

