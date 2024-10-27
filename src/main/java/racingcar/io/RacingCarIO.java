 package racingcar.io;

import static racingcar.constant.RacingCarConstant.*;

import camp.nextstep.edu.missionutils.Console;
import racingcar.dto.InputDTO;
import racingcar.dto.StatusDTO;
import racingcar.validation.Validator;

import java.util.*;

public class RacingCarIO {

    /**
     * 문제형식을 준수하여 입력값을 가져와 입력표준DTO로 전달합니다
     * @return 입력표준DTO
     * @throws IllegalArgumentException 부적절한 값이 입력된 경우
     */
    public static InputDTO getInput() {

        System.out.println( INPUT_MSG_A );
        String carNamesInput = Console.readLine() + " ";

        List<String> carNames = Arrays.stream( carNamesInput.split( "," ) )
                .map( String::trim )
                .map( Validator::checkCarName )
                .toList();

        System.out.println( INPUT_MSG_B );
        int tryCnt = 0;

        try {
            tryCnt = Integer.parseInt( Console.readLine() );
        } catch( Error ignored ) {}
        finally {
            Validator.checkTryCnt( tryCnt );
        }

        return new InputDTO( carNames, tryCnt );
    }

    /**
     * 문제형식을 준수하여 현재 진행중인 경주상태를 출력합니다.
     * @param statusDto 현재 상태정보를 담고있는 객체
     */
    public static void printStatus( StatusDTO statusDto ) {
        Map<String, Integer> status = statusDto.status();
        for ( String key: status.keySet() ) {
            Integer value = status.get(key);
            String result_msg = String.format(OUTPUT_STAGE_FORMAT, key, "-".repeat( value ));
            System.out.println( result_msg );
        }
        System.out.println();
    }

    /**
     * 문제형식을 준수하여 최종 경주결과를 출력합니다.
     * @param statusDTO 마지막 경주상태정보를 담고있는 객체
     */
    public static void printResult( StatusDTO statusDTO ) {
        Map<String, Integer> status = statusDTO.status();
        List<String> winners = new ArrayList<>( status.size() );
        int max_dist = -1;
        for ( String key: status.keySet() ) {
            Integer value = status.get(key);
            if ( value.compareTo( max_dist ) < 0 ) continue;
            if ( value.compareTo( max_dist ) > 0 ) {
                max_dist = value;
                winners.clear();
            }
            winners.add( key );
        }

        String winners_print = winners.toString().replaceAll("[\\[\\]]", "");
        System.out.println( String.format( OUTPUT_MSG_B, winners_print ) );
    }
}
