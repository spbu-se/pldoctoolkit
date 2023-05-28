from typing import no_type_check
import click

import TextDuplicateSearch as tds


@no_type_check
@click.command()
@click.argument('input', type=str)
@click.argument('output', type=str)
@click.argument('type', type=int, default=0)
@click.option('--search', required=True, type=click.Choice(['strict', 'fuzzy'], case_sensitive=False), help="")
@click.option('--preprocess', is_flag=True, default=False, help="Whether to preprocess input text")
@click.option('--min_length', type=int, default=3, help="Minimal duplicate length in tokens")
@click.option('--fragment_size', type=int, default=20, help="Size of fragments to split text into")
@click.option('--edit_distance', type=int, default=2, help="Maximal edit distance between fragments")
@click.option('--hash_diff', type=int, default=2, help="Maximal hash difference between fragments")
@click.option('--ngram', type=int, default=3, help="N in ngrams")
@click.option('--overlap', type=float, default=0.5, help="Threshold for ngram sets overlap")
def cli(input: str, output: str, type: int, search: str, preprocess: bool, min_length: int, fragment_size: int, edit_distance: int, hash_diff: int, ngram: int, overlap: float) -> None:
    """
    input - path to input file

    output - path to output filet

    type - searcher type
    """

    config = tds.create_config()

    config.input_file = input
    config.output_file = output
    config.searcher_type = type

    config.need_text_processing = preprocess

    config.min_dup_length = min_length
    config.fragment_size = fragment_size
    config.max_hashing_diff = hash_diff
    config.max_edit_distance = edit_distance

    config.ngram_n = ngram
    config.min_overlap = overlap

    if search == 'strict':
        tds.strict_search(config)

    if search == 'fuzzy':
        tds.fuzzy_search(config)


if __name__ == "__main__":
    cli()
