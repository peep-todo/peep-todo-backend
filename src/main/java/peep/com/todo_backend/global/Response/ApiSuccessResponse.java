package peep.com.todo_backend.global.Response;

public final class ApiSuccessResponse {
    public static final String USER_SAVE = "{\r\n" + //
            "  \"statusCode\": \"OK\",\r\n" + //
            "  \"resultMsg\": \"SUCCESS\",\r\n" + //
            "  \"resultDetail\": null,\r\n" + //
            "  \"resultData\": \"회원가입 성공\"\r\n" + //
            "}";

    public static final String USER_GET = "{\r\n" + //
            "  \"statusCode\": \"OK\",\r\n" + //
            "  \"resultMsg\": \"SUCCESS\",\r\n" + //
            "  \"resultDetail\": \"회원 정보 조회 성공\",\r\n" + //
            "  \"resultData\": {\r\n" + //
            "    \"user\": {\r\n" + //
            "      \"id\": 1,\r\n" + //
            "      \"nickname\": \"유나낭\",\r\n" + //
            "      \"profile\": \"\",\r\n" + //
            "      \"email\": \"admin@gmail.com\",\r\n" + //
            "      \"token\": \"\"\r\n" + //
            "    },\r\n" + //
            "    \"teams\": [\r\n" + //
            "      {\r\n" + //
            "        \"name\": \"PEEP\",\r\n" + //
            "        \"projectName\": \"TODO\",\r\n" + //
            "        \"description\": \"할 일을 관리하는 프로젝트\",\r\n" + //
            "        \"startDate\": \"2025-03-20\",\r\n" + //
            "        \"endDate\": \"2021-04-05\",\r\n" + //
            "        \"id\": 1,\r\n" + //
            "        \"teamToken\": \"7459e34e-69aa-48a6-b3ab-db223b72f936\"\r\n" + //
            "      },\r\n" + //
            "      {\r\n" + //
            "        \"name\": \"PEEP\",\r\n" + //
            "        \"projectName\": \"TODO\",\r\n" + //
            "        \"description\": \"할 일을 관리하는 프로젝트\",\r\n" + //
            "        \"startDate\": \"2025-03-20\",\r\n" + //
            "        \"endDate\": \"2021-04-05\",\r\n" + //
            "        \"id\": 2,\r\n" + //
            "        \"teamToken\": \"0861e120-1b43-4c94-9d4f-6174bbcb1701\"\r\n" + //
            "      }\r\n" + //
            "    ]\r\n" + //
            "  }\r\n" + //
            "}";

    public static final String USER_UPDATE = "{\r\n" + //
            "  \"statusCode\": \"OK\",\r\n" + //
            "  \"resultMsg\": \"SUCCESS\",\r\n" + //
            "  \"resultDetail\": null,\r\n" + //
            "  \"resultData\": \"회원 정보 수정 성공\"\r\n" + //
            "}";

    public static final String USER_PASSWORD_UPDATE = "{\r\n" + //
            "  \"statusCode\": \"OK\",\r\n" + //
            "  \"resultMsg\": \"SUCCESS\",\r\n" + //
            "  \"resultDetail\": null,\r\n" + //
            "  \"resultData\": \"비밀번호 변경 성공\"\r\n" + //
            "}";

    public static final String USER_DELETE = "{\r\n" + //
            "  \"statusCode\": \"OK\",\r\n" + //
            "  \"resultMsg\": \"SUCCESS\",\r\n" + //
            "  \"resultDetail\": null,\r\n" + //
            "  \"resultData\": \"회원 삭제 성공\"\r\n" + //
            "} ";

    public static final String USER_LOGIN = "{\n" + //
            "  \"headers\": {},\r\n" + //
            "  \"body\": {\r\n" + //
            "    \"accessToken\": \"eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImVtYWlsIjoiYWRtaW5AZ21haWwuY29tIiwiaWF0IjoxNzQyOTA1NjAyLCJleHAiOjE3NDI5MDkyMDJ9.jKoig49jlZwq2p2wufpR6jqX2ZMiCsuGSAosBc6b6y4\",\r\n"
            + //
            "    \"refreshToken\": \"eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImVtYWlsIjoiYWRtaW5AZ21haWwuY29tIiwiaWF0IjoxNzQyOTA1NjAyLCJleHAiOjE3NDM1MTA0MDJ9.KxYWO_HvsOc4toYSCvdMUCiiIiNgH7_TYCKzCdI1jvM\"\r\n"
            + //
            "  },\r\n" + //
            "  \"statusCode\": \"OK\",\r\n" + //
            "  \"statusCodeValue\": 200\r\n" + //
            "}";

    public static final String TEAM_SAVE = "{\r\n" + //
            "  \"statusCode\": \"OK\",\r\n" + //
            "  \"resultMsg\": \"SUCCESS\",\r\n" + //
            "  \"resultDetail\": \"팀이 성공적으로 생성되었습니다.\",\r\n" + //
            "  \"resultData\": \"https://localhost:8080/invite/9c6167c5-b6db-49c2-a67b-27213a34b449\"\r\n" + //
            "}";

    public static final String TEAM_GET = "{\r\n" + //
            "  \"statusCode\": \"OK\",\r\n" + //
            "  \"resultMsg\": \"SUCCESS\",\r\n" + //
            "  \"resultDetail\": \"팀 정보 조회 성공\",\r\n" + //
            "  \"resultData\": {\r\n" + //
            "    \"name\": \"PEEP2\",\r\n" + //
            "    \"projectName\": \"TODO\",\r\n" + //
            "    \"description\": \"할 일을 관리하는 프로젝트\",\r\n" + //
            "    \"startDate\": \"2025-03-20\",\r\n" + //
            "    \"endDate\": \"2021-04-05\",\r\n" + //
            "    \"id\": 3,\r\n" + //
            "    \"teamToken\": null\r\n" + //
            "  }\r\n" + //
            "}";

    public static final String CATEGORY_SAVE = "{\r\n" + //
        "  \"statusCode\": \"OK\",\r\n" + //
        "  \"resultMsg\": \"SUCCESS\",\r\n" + //
        "  \"resultDetail\": \"카테고리 생성 성공\",\r\n" + //
        "  \"resultData\": \"null\"\r\n" + //
        "}";

    public static final String CATEGORY_GET = "{\n"
        + "  \"statusCode\": \"OK\",\n"
        + "  \"resultMsg\": \"SUCCESS\",\n"
        + "  \"resultDetail\": \"카테고리 목록 조회 성공\",\n"
        + "  \"resultData\": [\n"
        + "    {\n"
        + "      \"categoryId\": 1,\n"
        + "      \"name\": \"알바\",\n"
        + "      \"color\": \"#CBACFF\",\n"
        + "      \"pinned\": false,\n"
        + "      \"categoryType\": \"PERSONAL\"\n"
        + "    }\n"
        + "  ]\n"
        + "}";

    public static final String CATEGORY_UPDATE = "{\r\n" + //
        "  \"statusCode\": \"OK\",\r\n" + //
        "  \"resultMsg\": \"SUCCESS\",\r\n" + //
        "  \"resultDetail\": null,\r\n" + //
        "  \"resultData\": \"카테고리 수정 성공\"\r\n" + //
        "}";

    public static final String CATEGORY_DELETE = "{\r\n" + //
        "  \"statusCode\": \"OK\",\r\n" + //
        "  \"resultMsg\": \"SUCCESS\",\r\n" + //
        "  \"resultDetail\": null,\r\n" + //
        "  \"resultData\": \"카테고리 삭제 성공\"\r\n" + //
        "} ";
}
