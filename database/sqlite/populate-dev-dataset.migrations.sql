INSERT INTO passport_user VALUES (
    "85ff5a54-48b9-4294-a91d-2be50bd2a77d"
);

INSERT INTO passport_visa VALUES (
    "670cc2e7-9a9c-4273-9334-beb40d364e5c",
    "StarterKitDatasetsControlledAccessGrants",
    "https://datasets.starterkit.ga4gh.org/",
    "Controlled access dev datasets for the GA4GH Starter Kit"
);

INSERT INTO passport_visa_assertion (user_id, visa_id, status) VALUES (
    "85ff5a54-48b9-4294-a91d-2be50bd2a77d",
    "670cc2e7-9a9c-4273-9334-beb40d364e5c",
    "active"
);
