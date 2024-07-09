import React from "react";
import {CSDEEP_ADMIN_INFO} from "$src/AppContext";

export const LeftMenu = (props: {
    logout: () => void,
    activeItem: string
}): JSX.Element => {
    return (
        <div className="h-full w-[200px] bg-slate-100">
            <nav
                className="relative h-full flex w-full flex-col items-center justify-between bg-[#FBFBFB] py-2 text-neutral-500 shadow-lg hover:text-neutral-700 focus:text-neutral-700 dark:bg-neutral-600 lg:py-4"
                data-te-navbar-ref>
                <div className="flex w-full flex-col items-center justify-between">
                    <div className="h-[30px]">
                        <a className="mx-2 my-1 flex items-center text-neutral-900 hover:text-neutral-900 focus:text-neutral-900 lg:mb-0 lg:mt-0"
                           href="#">
                            <img className="mr-2"
                                 src="https://tecdn.b-cdn.net/img/logo/te-transparent-noshadows.webp"
                                 style={{height: '20px'}}
                                 alt="TE Logo"
                                 loading="lazy"/>
                        </a>
                    </div>
                    <button
                        className="block border-0 bg-transparent px-2 text-neutral-500 hover:no-underline hover:shadow-none focus:no-underline focus:shadow-none focus:outline-none focus:ring-0 dark:text-neutral-200 lg:hidden"
                        type="button"
                        data-te-collapse-init
                        data-te-target="#navbarSupportedContent4"
                        aria-controls="navbarSupportedContent4"
                        aria-expanded="false"
                        aria-label="Toggle navigation">
                                        <span className="[&>svg]:w-7">
                                            <svg
                                                xmlns="http://www.w3.org/2000/svg"
                                                viewBox="0 0 24 24"
                                                fill="currentColor"
                                                className="h-7 w-7">
                                              <path
                                                  fill-rule="evenodd"
                                                  d="M3 6.75A.75.75 0 013.75 6h16.5a.75.75 0 010 1.5H3.75A.75.75 0 013 6.75zM3 12a.75.75 0 01.75-.75h16.5a.75.75 0 010 1.5H3.75A.75.75 0 013 12zm0 5.25a.75.75 0 01.75-.75h16.5a.75.75 0 010 1.5H3.75a.75.75 0 01-.75-.75z"
                                                  clip-rule="evenodd"/>
                                            </svg>
                                        </span>
                    </button>
                    <div className="!visible hidden basis-[100%] items-center lg:mt-0 lg:!flex lg:basis-auto w-full"
                         id="navbarSupportedContent4"
                         data-te-collapse-item>
                        <ul className="list-style-none mr-auto pl-0 lg:mt-1 lg:flex-row w-full"
                            data-te-navbar-nav-ref>
                            <li className={'my-4 pl-2 lg:my-0 lg:pl-2 lg:pr-1 flex items-center border-b-2 border-t-2 h-[50px]' + (props.activeItem == CSDEEP_ADMIN_INFO.code ? ' bg-stone-200' : '')}
                                data-te-nav-item-ref>
                                <a className={'text-neutral-500 hover:text-neutral-700 focus:text-neutral-700 disabled:text-black/30 dark:text-neutral-200 dark:hover:text-neutral-400 dark:focus:text-neutral-400 lg:px-2' + (props.activeItem == CSDEEP_ADMIN_INFO.code ? ' text-black/90' : '')}
                                   aria-current="page"
                                   href="/#/admin/info"
                                   data-te-nav-link-ref>Общая информация</a>
                            </li>
                            <li className="my-4 pl-2 lg:my-0 lg:pl-2 lg:pr-1 flex items-center border-b-2 h-[50px]"
                                data-te-nav-item-ref>
                                <a className="font-bold text-neutral-500 hover:text-neutral-700 focus:text-neutral-700 disabled:text-black/30 dark:text-neutral-200 dark:hover:text-neutral-400 dark:focus:text-neutral-400 lg:px-2 [&.active]:text-black/90 dark:[&.active]:text-neutral-400"
                                   aria-current="page"
                                   href="/#/admin"
                                   onClick={props.logout}
                                   data-te-nav-link-ref>Выход</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
    )

}